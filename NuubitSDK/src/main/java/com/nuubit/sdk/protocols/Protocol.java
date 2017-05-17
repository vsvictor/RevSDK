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

import okhttp3.Interceptor;
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
    private long beginTime;
    private long endTime;


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

    public void save(Request original, Request result, Response response, EnumProtocol protocol, long beginTime, long endTime, long firsByteTime){
        RequestOne statRequest = null;
        try {
            statRequest = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, endTime, firsByteTime);
            if(statRequest.getFirstByteTime() == 0) statRequest.setFirstByteTime(statRequest.getEndTS());
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
            Log.i("database", statRequest.toString());
        } catch (NullPointerException ex) {
            Log.i("database", "Standard exception Database error!!!");
            ex.printStackTrace();
        }
    }

    public void save(RequestOne req){
        try {
            if(req.getFirstByteTime() == 0) req.setFirstByteTime(req.getEndTS());
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), req));
            Log.i("database", req.toString());
        } catch (NullPointerException ex) {
            Log.i("database", "Standard exception Database error!!!");
            ex.printStackTrace();
        }
    }
    protected boolean isOrigin(){
        return ((NuubitApplication.getInstance().getConfig().getParam().get(0).getOperationMode() == OperationMode.report_only)||
                (NuubitApplication.getInstance().getConfig().getParam().get(0).getOperationMode() == OperationMode.off));
    }
    protected Request preHandler(Interceptor.Chain chain){
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
    protected Response postHandler(Response response){
        try {
            RequestOne req = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, 0, response.receivedResponseAtMillis());
            req.setStatusCode(response.code());
            String cache = (response == null ? NuubitConstants.UNDEFINED : response.header("x-rev-cache"));
            req.setXRevCache(cache == null ? NuubitConstants.UNDEFINED : cache);

            endTime = System.currentTimeMillis();
            req.setEndTS(endTime);

            if (response == null) {
                throw new HTTPException(original, result, response, this, beginTime, endTime);
            }
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() == HTTPCode.Type.SERVER_ERROR) {
                throw new HTTPException(original, result, response, this, beginTime, endTime);
            }
            if (!isSystem(original)) {
                this.zeroing();
            }
            boolean r = (code.getType() == HTTPCode.Type.INFORMATIONAL) || (code.getType() == HTTPCode.Type.SUCCESSFULL) || (code.getType() == HTTPCode.Type.REDIRECTION);
            req.setSuccessStatus(r ? 1 : 0);
            save(req);

            NuubitApplication.getInstance().getRequestCounter().addRequest(response.request(), EnumProtocol.STANDARD);
            long reqBodySize = 0;
            try {
                RequestBody reqBody = original.body();
                reqBodySize = (reqBody == null? 0:reqBody.contentLength());
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
        } catch (HTTPException ex) {
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addFailRequest();
            this.errorIncrement();
            if (this.isOverflow()) {
                NuubitApplication.getInstance().removeProtocol(EnumProtocol.createInstance(this.getDescription()));
                NuubitApplication.getInstance().sendBroadcast(new Intent(NuubitActions.RETEST));
                this.zeroing();
            }
            if(!isOrigin()) {
                NuubitApplication.getInstance().getProtocolCounters().get("standart").addFailRequest();
            }
            else {
                NuubitApplication.getInstance().getProtocolCounters().get("origin").addFailRequest();
            }
            ex.printStackTrace();
        }
        return response;
    }
}
