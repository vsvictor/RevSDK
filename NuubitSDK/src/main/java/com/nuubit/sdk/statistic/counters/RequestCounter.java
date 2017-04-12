package com.nuubit.sdk.statistic.counters;

import android.content.SharedPreferences;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.protocols.TypeRequest;
import com.nuubit.sdk.types.Pair;

import java.util.ArrayList;

import okhttp3.Request;

/*
 * ************************************************************************
 *
 *
 * NUU:BIT CONFIDENTIAL
 * [2013] - [2017] NUU:BIT, INC.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of NUU:BIT, INC. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to NUU:BIT, INC.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from NUU:BIT, INC.
 *
 * Victor D. Djurlyak, 2017
 *
 * /
 */

public class RequestCounter extends Counters {
    private long revRequests;
    private long originRequests;
    private long systemRequests;
    private long standartProtocol;
    private long quicProtocol;
    private long revProtocol;

    public void addRequest(Request req, EnumProtocol enumProtocol) {
        String key = NuubitApplication.getInstance().getSDKKey();
        String host = req.url().host();
        if (NuubitSDK.isSystem(req)) systemRequests++;
        if (!NuubitSDK.isSystem(req) && host.contains(key)) revRequests++;
        else if (!NuubitSDK.isSystem(req)) originRequests++;
        switch (enumProtocol) {
            case STANDART: {
                standartProtocol++;
                break;
            }
            case QUIC: {
                quicProtocol++;
                break;
            }
            case RMP: {
                revProtocol++;
                break;
            }
        }
    }

    @Override
    public void save(SharedPreferences share) {
        SharedPreferences.Editor editor = share.edit();
        editor.putLong(NuubitConstants.REV, getRevRequestsCount());
        editor.putLong(NuubitConstants.ORIGIN, getOriginRequestsCount());
        editor.putLong(NuubitConstants.SYSTEM, getSystemRequestsCount());
        editor.putLong(EnumProtocol.STANDART.toString(), standartProtocol);
        editor.putLong(EnumProtocol.QUIC.toString(), quicProtocol);
        editor.putLong(EnumProtocol.RMP.toString(), revProtocol);
        editor.commit();
    }

    @Override
    public void load(SharedPreferences share) {
        revRequests = share.getLong(NuubitConstants.REV, 0);
        originRequests = share.getLong(NuubitConstants.ORIGIN, 0);
        systemRequests = share.getLong(NuubitConstants.SYSTEM, 0);
        standartProtocol = share.getLong(EnumProtocol.STANDART.toString(), 0);
        quicProtocol = share.getLong(EnumProtocol.QUIC.toString(), 0);
        revProtocol = share.getLong(EnumProtocol.RMP.toString(), 0);
    }

    public long getRequestCount(TypeRequest type) {
        long result = 0;
        switch (type) {
            case ALL: {
                result = getRevRequestsCount()
                        + getOriginRequestsCount()
                        + getSystemRequestsCount();
                break;
            }
            case NUUBIT: {
                result = getRevRequestsCount();
                break;
            }
            case ORIGIN: {
                result = getOriginRequestsCount();
                break;
            }
            case SYSTEM: {
                result = getSystemRequestsCount();
                break;
            }
        }
        return result;
    }

    private long getAllRequest() {
        return getRevRequestsCount() + getOriginRequestsCount() + getSystemRequestsCount();
    }
    private long getRevRequestsCount() {
        return revRequests;
    }

    private long getOriginRequestsCount() {
        return originRequests;
    }

    private long getSystemRequestsCount() {
        return systemRequests;
    }

    public long getRequestsOver(EnumProtocol enumProtocol) {
        long result = 0;
        switch (enumProtocol) {
            case STANDART: {
                result = getRequestsOverStandart();
                break;
            }
            case QUIC: {
                result = getRequestsOverQUIC();
                break;
            }
            case RMP: {
                result = getRequestsOverRPM();
                break;
            }
        }
        return result;
    }

    private long getRequestsOverStandart() {
        return standartProtocol;
    }

    private long getRequestsOverQUIC() {
        return quicProtocol;
    }

    private long getRequestsOverRPM() {
        return revProtocol;
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("allRequest", String.valueOf(getAllRequest())));
        result.add(new Pair("revRequests", String.valueOf(getRevRequestsCount())));
        result.add(new Pair("originRequests", String.valueOf(getOriginRequestsCount())));
        result.add(new Pair("systemRequests", String.valueOf(getSystemRequestsCount())));
        result.add(new Pair("standartProtocol", String.valueOf(getRequestsOverStandart())));
        result.add(new Pair("quicProtocol", String.valueOf(getRequestsOverQUIC())));
        result.add(new Pair("rpmProtocol", String.valueOf(getRequestsOverRPM())));
        return result;
    }
}
