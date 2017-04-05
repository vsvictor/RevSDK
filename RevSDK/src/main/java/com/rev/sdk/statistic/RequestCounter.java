package com.rev.sdk.statistic;

import android.content.SharedPreferences;

import com.rev.sdk.Constants;
import com.rev.sdk.RevApplication;
import com.rev.sdk.RevSDK;
import com.rev.sdk.protocols.EnumProtocol;
import com.rev.sdk.protocols.TypeRequest;
import com.rev.sdk.types.Pair;

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

public class RequestCounter {
    private long revRequests;
    private long originRequests;
    private long systemRequests;
    private long standartProtocol;
    private long quicProtocol;
    private long revProtocol;

    public void addRequest(Request req, EnumProtocol enumProtocol) {
        String key = RevApplication.getInstance().getSDKKey();
        String host = req.url().host();
        if (RevSDK.isSystem(req)) systemRequests++;
        if (!RevSDK.isSystem(req) && host.contains(key)) revRequests++;
        else if (!RevSDK.isSystem(req)) originRequests++;
        switch (enumProtocol) {
            case STANDART: {
                standartProtocol++;
                break;
            }
            case QUIC: {
                quicProtocol++;
                break;
            }
            case REV: {
                revProtocol++;
                break;
            }
        }
    }

    public void save(SharedPreferences share) {
        SharedPreferences.Editor editor = share.edit();
        editor.putLong(Constants.REV, getRevRequestsCount());
        editor.putLong(Constants.ORIGIN, getOriginRequestsCount());
        editor.putLong(Constants.SYSTEM, getSystemRequestsCount());
        editor.putLong(EnumProtocol.STANDART.toString(), standartProtocol);
        editor.putLong(EnumProtocol.QUIC.toString(), quicProtocol);
        editor.putLong(EnumProtocol.REV.toString(), revProtocol);
        editor.commit();
    }

    public void load(SharedPreferences share) {
        revRequests = share.getLong(Constants.REV, 0);
        originRequests = share.getLong(Constants.ORIGIN, 0);
        systemRequests = share.getLong(Constants.SYSTEM, 0);
        standartProtocol = share.getLong(EnumProtocol.STANDART.toString(), 0);
        quicProtocol = share.getLong(EnumProtocol.QUIC.toString(), 0);
        revProtocol = share.getLong(EnumProtocol.REV.toString(), 0);
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
            case REV: {
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
            case REV: {
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
