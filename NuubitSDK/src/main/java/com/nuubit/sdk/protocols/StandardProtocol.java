package com.nuubit.sdk.protocols;

import android.content.Intent;
import android.util.Log;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.interseptor.ProgressResponseBody;
import com.nuubit.sdk.statistic.counters.ProtocolCounters;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import static com.nuubit.sdk.NuubitSDK.isFree;
import static com.nuubit.sdk.NuubitSDK.isStatistic;
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

public class StandardProtocol extends Protocol {
    private static final String TAG = StandardProtocol.class.getSimpleName();
    private HTTPException prevException;
    private Request original;
    private Request result;
    private Response response;
    private long beginTime;
    private long endTime;

    //private ProtocolCounters standartCounter;
    //private ProtocolCounters originCounter;
    public StandardProtocol() {
        super();
        this.descroption = EnumProtocol.STANDARD;
        //standartCounter = NuubitApplication.getInstance().getProtocolCounters().get("standart");
        //originCounter = NuubitApplication.getInstance().getProtocolCounters().get("origin");
    }

    @Override
    public synchronized Response send(Interceptor.Chain chain) throws IOException {
        result = null;
        original = chain.request();
        RequestBody reqBody = original.body();
        long reqBodySize = reqBody == null? 0:reqBody.contentLength();
        boolean systemRequest = isSystem(original);
        boolean freeRequest = isFree(original);
        if (!systemRequest && !freeRequest) {
            result = NuubitSDK.processingRequest(original, true);
        } else {
            Log.i("System", original.toString());
            result = original;
        }
        response = null;
        beginTime = 0;
        endTime = 0;
        //ProgressResponseBody pb = null;
        try {
            beginTime = System.currentTimeMillis();
            response = chain.proceed(result);
            RequestOne req = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, 0, response.receivedResponseAtMillis());
            req.setStatusCode(response.code());
/*
            response = resp.newBuilder()
                    .body(new ProgressResponseBody(resp.body(), listener, req))
                    .build();
*/
            //RequestOne req = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription(), beginTime, 0, 0);
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
            req.setSuccessStatus(r?1:0);
            save(req);
        } catch (IOException ex){
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addFailRequest();
            ex.printStackTrace();
            Log.i("Timeout", ex.getMessage()+" my timeout");
        } catch (HTTPException ex) {
            response = chain.proceed(original);
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
        NuubitApplication.getInstance().getRequestCounter().addRequest(response.request(), EnumProtocol.STANDARD);
        if(!isOrigin()) {
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addSuccessRequest();
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addSent(reqBodySize);
        } else{
            NuubitApplication.getInstance().getProtocolCounters().get("origin").addSuccessRequest();
            NuubitApplication.getInstance().getProtocolCounters().get("origin").addSent(reqBodySize);
        }
        return response;
    }

    @Override
    public TestOneProtocol test(String url) {
        TestOneProtocol res = new TestOneProtocol(EnumProtocol.STANDARD);
        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true));
        builder.url(url);
        Call callback = NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false).newCall(builder.build());
        try {
            long begTime = System.currentTimeMillis();
            Response response = callback.execute();
            long endTime = System.currentTimeMillis();
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() != HTTPCode.Type.SUCCESSFULL) {
                res.setTime(-1);
            } else {
                res.setTime(endTime-begTime);
            }
            res.setTimeEnded(endTime);
            res.setReason(code.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            res.setTime(-1);
            res.setTimeEnded(System.currentTimeMillis());
            res.setReason(NuubitConstants.UNDEFINED);
        }
        return res;
    }
/*
    private ProgressResponseBody.ProgressListener listener = new ProgressResponseBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {

        }

        @Override
        public void firstByteTime(long time) {
        }

        @Override
        public void lastByteTime(long time) {

        }

        @Override
        public void onRequest(RequestOne req){
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addSent(req.getSentBytes());
            NuubitApplication.getInstance().getProtocolCounters().get("standard").addReceive(req.getReceivedBytes());
            Log.i("ZERROFBT","Request");
            if(req.getFirstByteTime() == 0){
                Log.i("ZERROFBT",String.valueOf(req.getFirstByteTime()));
                req.setFirstByteTime(req.getEndTS());
                Log.i("ZERROFBT",String.valueOf(req.getFirstByteTime()));
            }
            save(req);
        }

        @Override
        public void onResponse(Response response, boolean serv, long lastByteTime) {

        }
    };
*/
}
