package com.nuubit.sdk.protocols;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.counters.ProtocolCounters;
import com.nuubit.sdk.statistic.sections.RequestOne;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

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

public abstract class Protocol implements OnFuncProtocol {
    protected static int errorCounter;
    protected EnumProtocol descroption;
    protected ProtocolCounters counter;

    public Protocol() {
    }

    public static Protocol fromString(@NonNull String proto) {
        if (proto.equalsIgnoreCase("rmp")) return new RMPProtocol();
        else if (proto.equalsIgnoreCase("quic")) return new QUICProtocol();
        else return new StandardProtocol();
    }

    public EnumProtocol getDescription() {
        return descroption;
    }

    public void errorIncrement() {
        this.errorCounter++;
    }

    public boolean isOverflow() {
        return errorCounter >= NuubitConstants.ERRORS_IN_ROW;
    }

    public void zeroing() {
        errorCounter = 0;
    }

    public ProtocolCounters getCounter() {
        return counter;
    }

    public void save(Request original, Request result, Response response, EnumProtocol protocol, long beginTime, long endTime, long firsByteTime){
        RequestOne statRequest = null;
        try {
            statRequest = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, endTime, firsByteTime);
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
            Log.i("database", statRequest.toString());
            counter.addSuccessRequest();
            //counter.addReceive(getResponseSize(response));
        } catch (NullPointerException ex) {
            //NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
            Log.i("database", "Standart exception Database error!!!");
            counter.addFailRequest();
            ex.printStackTrace();
        }
    }

    public void save(RequestOne req){
        //RequestOne statRequest = null;
        try {
            //statRequest = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, endTime, firsByteTime);
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), req));
            Log.i("database", req.toString());
            counter.addSuccessRequest();
            //counter.addReceive(getResponseSize(response));
        } catch (NullPointerException ex) {
            //NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
            Log.i("database", "Standart exception Database error!!!");
            counter.addFailRequest();
            ex.printStackTrace();
        }
    }

}
