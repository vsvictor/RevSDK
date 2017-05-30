package com.nuubit.sdk.protocols;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.counters.ProtocolCounters;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import static com.nuubit.sdk.NuubitSDK.isFree;
import static com.nuubit.sdk.NuubitSDK.isSystem;

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
    //protected NuubitApplication app;// = NuubitApplication.getInstance();
    protected static int errorCounter;
    protected EnumProtocol descroption;
    protected Object mutex = new Object();
    protected Request original;
    protected Request result;
    protected Response response;
    protected long beginTime;
    protected long endTime;

    private int count = 0;

    public Protocol() {
        //app = NuubitApplication.getInstance();
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

    public void save(Request original, Request result, Response response, EnumProtocol protocol, long beginTime, long endTime, long firsByteTime) {
        RequestOne statRequest = null;
        try {
            statRequest = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, endTime, firsByteTime);
            if (statRequest.getFirstByteTime() == 0)
                statRequest.setFirstByteTime(statRequest.getEndTS());
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
            Log.i("database", statRequest.toString());
        } catch (NullPointerException ex) {
            Log.i("database", "Standard exception Database error!!!");
            ex.printStackTrace();
        }
    }

    public void save(RequestOne req) {
        try {
            if (req.getFirstByteTime() == 0) req.setFirstByteTime(req.getEndTS());
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), req));
            Log.i("database", req.toString());
        } catch (NullPointerException ex) {
            Log.i("database", "Standard exception Database error!!!");
            ex.printStackTrace();
        }
    }

    protected boolean isOrigin() {
        return ((NuubitApplication.getInstance().getConfig().getParam().get(0).getOperationMode() == OperationMode.report_only) ||
                (NuubitApplication.getInstance().getConfig().getParam().get(0).getOperationMode() == OperationMode.off));
    }

    protected Request preHandler(Interceptor.Chain chain) {
        result = null;
        original = chain.request();
        boolean systemRequest = isSystem(original);
        boolean freeRequest = isFree(original);
        if (!systemRequest && !freeRequest) {
            result = NuubitSDK.processingRequest(original, true);
        } else {
            Log.i("System", original.toString());
            result = original;
        }
        response = null;
        beginTime = System.currentTimeMillis();
        endTime = 0;
        return result;
    }

    protected Response postHandler() {
        endTime = System.currentTimeMillis();
        if (response == null) {
            response = genrate0();
        }
        RequestOne req = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, 0, response.receivedResponseAtMillis());
        req.setStatusCode(response.code());
        String cache = (response.code() == 0 ? NuubitConstants.UNDEFINED : response.header("x-rev-cache"));
        req.setXRevCache(cache == null ? NuubitConstants.UNDEFINED : cache);

        req.setEndTS(endTime);
        HTTPCode code = HTTPCode.create(response.code());
        if (!isSystem(original)) {
            this.zeroing();
        }
        boolean r = (code.getType() == HTTPCode.Type.INFORMATIONAL) || (code.getType() == HTTPCode.Type.SUCCESSFULL) || (code.getType() == HTTPCode.Type.REDIRECTION);
        req.setSuccessStatus(r ? 1 : 0);
        save(req);

        NuubitApplication.getInstance().getRequestCounter().addRequest(response.request(), this.getDescription());
        long reqBodySize = 0;
        try {
            RequestBody reqBody = original.body();
            reqBodySize = (reqBody == null ? 0 : reqBody.contentLength());
        } catch (IOException ex) {
            reqBodySize = 0;
        }
        if (!isOrigin()) {
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addSuccessRequest();
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addSent(reqBodySize);
        } else {
            NuubitApplication.getInstance().getProtocolCounters().get("origin").addSuccessRequest();
            NuubitApplication.getInstance().getProtocolCounters().get("origin").addSent(reqBodySize);
        }
        count++;
        Log.i("REDIRECT","N "+String.valueOf(count)+" code:"+response.code()+" "+response.headers().toString());
        if(response.isRedirect()){
            String location = response.header("location");
            Log.i("REDIRECT", "N "+String.valueOf(count)+" Redirect from: "+response.request().url().toString()+" code:"+String.valueOf(response.code())+" to "+location);
            original = result.newBuilder().url(location).build();
            Log.i("REDIRECT", original.toString());
            try {
                NuubitSDK.getClient().newCall(original).execute();
            } catch (IOException e) {
                Log.i("REDIRECT", "Exception: "+e.getMessage());
                e.printStackTrace();
            }
        }
        return response;
    }

    private Response genrate0() {
        ResponseBody body = ResponseBody.create(MediaType.parse("text/pain"), "Response null");
        Response.Builder builder = new Response.Builder();
        builder.code(0).body(body).sentRequestAtMillis(beginTime).receivedResponseAtMillis(beginTime);
        builder.protocol(okhttp3.Protocol.HTTP_2);
        Set<String> names = result.headers().names();
        for (String name : names) {
            String value = result.header(name);
            builder.addHeader(name, value);
        }
        builder.request(result);
        return builder.build();
    }
}
