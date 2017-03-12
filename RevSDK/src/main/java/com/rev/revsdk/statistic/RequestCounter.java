package com.rev.revsdk.statistic;

import android.content.SharedPreferences;

import com.rev.revsdk.Constants;
import com.rev.revsdk.RevApplication;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.protocols.TypeRequest;
import com.rev.revsdk.types.Pair;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * Created by victor on 08.03.17.
 */

public class RequestCounter {
    private long revRequests;
    private long originRequests;
    private long systemRequests;
    private long standartProtocol;
    private long quicProtocol;
    private long revProtocol;

    public void addRequest(Request req, Protocol protocol) {
        String key = RevApplication.getInstance().getSDKKey();
        String host = req.url().host();
        if (RevSDK.isSystem(req)) systemRequests++;
        if (!RevSDK.isSystem(req) && host.contains(key)) revRequests++;
        else if (!RevSDK.isSystem(req)) originRequests++;
        switch (protocol) {
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
        editor.putLong(Protocol.STANDART.toString(), standartProtocol);
        editor.putLong(Protocol.QUIC.toString(), quicProtocol);
        editor.putLong(Protocol.REV.toString(), revProtocol);
        editor.commit();
    }

    public void load(SharedPreferences share) {
        revRequests = share.getLong(Constants.REV, 0);
        originRequests = share.getLong(Constants.ORIGIN, 0);
        systemRequests = share.getLong(Constants.SYSTEM, 0);
        standartProtocol = share.getLong(Protocol.STANDART.toString(), 0);
        quicProtocol = share.getLong(Protocol.QUIC.toString(), 0);
        revProtocol = share.getLong(Protocol.REV.toString(), 0);
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

    public long getRequestsOver(Protocol protocol) {
        long result = 0;
        switch (protocol) {
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
