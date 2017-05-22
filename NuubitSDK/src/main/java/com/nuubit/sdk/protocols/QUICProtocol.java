package com.nuubit.sdk.protocols;

import android.util.Log;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.statistic.counters.ProtocolCounters;
import com.nuubit.sdk.types.HTTPCode;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataProviders;
import org.chromium.net.UploadDataSink;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.chromium.net.UrlRequest.Builder.REQUEST_PRIORITY_HIGHEST;

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

public class QUICProtocol extends Protocol {
    private static final String TAG = QUICProtocol.class.getSimpleName();
    private CronetEngine engine;
    private Executor executor;
    private String userAgent;
    public QUICProtocol() {
        this.descroption = EnumProtocol.QUIC;
        CronetEngine.Builder builder = new CronetEngine.Builder(NuubitApplication.getInstance());
        builder
                .addQuicHint("monitor.revsw.net", 443, 443)
                .enableQuic(true)
                .enableHttp2(true);
        engine = builder.build();
        userAgent = System.getProperty("http.agent");
        executor = Executors.newSingleThreadExecutor();
    }
    @Override
    public Response send(Interceptor.Chain chain) {
        result = preHandler(chain);
        UrlRequest req = OkHTTPRequest2CronetRequest(result);
        synchronized (mutex) {
            req.start();
            try {
                mutex.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public TestOneProtocol test(String url) {
        TestOneProtocol res = new TestOneProtocol(EnumProtocol.QUIC);
        UrlRequest.Callback callback = new SimpleUrlRequestCallback(res);
        UrlRequest.Builder builder = engine.newUrlRequestBuilder(url, callback, executor)
                .addHeader("Alternate-Protocol","443:quic")
                .addHeader("User-Agent",userAgent);
        UrlRequest req = builder.build();
        long begTime = System.currentTimeMillis();
        res.setStartTime(begTime);
        synchronized (mutex) {
            req.start();
            try {
                mutex.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("TESTPROTOCOL",EnumProtocol.QUIC.toString()+" "+res.toString());
        Log.i("TESTPROTOCOL",url);
        return res;
    }

    private UrlRequest OkHTTPRequest2CronetRequest(Request req){
        UrlRequest.Callback callback = new RealQUICRequestCallback(System.currentTimeMillis());
        UrlRequest.Builder builder = engine.newUrlRequestBuilder(req.url().toString(), callback, executor);
        Headers headers = req.headers();
        RequestBody body = req.body();
        Set<String> names = headers.names();
        for(String name : names){
            String value = headers.get(name);
            builder.addHeader(name, value);
        }
        String sMethod = req.method();
        builder.setHttpMethod(sMethod);
        if(sMethod.equalsIgnoreCase("POST")){
            if(body != null) {
                String sBody = body.toString();
                builder.setUploadDataProvider(
                        UploadDataProviders.create(sBody.getBytes()), executor);
            }
        }
        builder.setPriority(REQUEST_PRIORITY_HIGHEST);
        return builder.addHeader("Alternate-Protocol","443:quic")
                .addHeader("User-Agent",userAgent)
                .build();
    }
    private class SimpleUrlRequestCallback extends UrlRequest.Callback {
        private TestOneProtocol test;

        public SimpleUrlRequestCallback(){
            test = new TestOneProtocol(EnumProtocol.QUIC);
        }
        public SimpleUrlRequestCallback(TestOneProtocol test){
            this.test = test;
        }

        @Override
        public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
            Log.i(TAG, "****** onRedirectReceived ******");
            request.followRedirect();
        }

        @Override
        public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
            Log.i("RESPONSE", "****** Request Started, status code is " + info.getHttpStatusCode() + ", total received bytes is " + info.getReceivedByteCount() + ", protocol - " + info.getNegotiatedProtocol());
            synchronized (mutex) {
                Log.i(TAG, "****** Response Started ******");
                long endTime = System.currentTimeMillis();
                test.setTimeEnded(endTime);
                test.setTime(endTime - test.getStartTime());
                test.setReason(HTTPCode.create(info.getHttpStatusCode()).getMessage());
                //mutex.notifyAll();
            }

        }

        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
            //synchronized (mutex) {
                Log.i("RESPONSE", "****** Request Successed, status code is " + info.getHttpStatusCode() + ", total received bytes is " + info.getReceivedByteCount() + ", protocol - " + info.getNegotiatedProtocol());
           //     long endTime = System.currentTimeMillis();
           //     test.setTimeEnded(endTime);
           //     test.setTime(endTime - test.getStartTime());
           //     mutex.notifyAll();
           // }
        }

        @Override
        public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
            Log.i("RESPONSE", "****** Request Completed, status code is " + info.getHttpStatusCode() + ", total received bytes is " + info.getReceivedByteCount()+", protocol - "+info.getNegotiatedProtocol());
        }

        @Override
        public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
            Log.i("RESPONSE", "****** Request Fail, status code is " + info.getHttpStatusCode() + ", total received bytes is " + info.getReceivedByteCount()+", protocol - "+info.getNegotiatedProtocol());
            synchronized (mutex) {
                Log.i(TAG, "****** onFailed, error is: " + error.getMessage());
                long endTime = System.currentTimeMillis();
                test.setTimeEnded(endTime);
                test.setTime(-1);
                test.setReason(error.getMessage());
                mutex.notifyAll();
            }
        }
    }

    private class RealQUICRequestCallback extends UrlRequest.Callback{
        private long startTime;
        private long firstByteTime;
        private Response.Builder builder;
        public RealQUICRequestCallback(long startTime){
            this.startTime = startTime;
            builder = new Response.Builder();
        }

        @Override
        public void onRedirectReceived(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, String s) throws Exception {
            synchronized (mutex) {
                Log.i(TAG, "---Redirect---");
                urlRequest.followRedirect();
            }
        }

        @Override
        public void onResponseStarted(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo) throws Exception {
            synchronized (mutex) {
            Log.i(TAG,"---ResponseStarted---");

                String contentType = "text/html";
                firstByteTime = System.currentTimeMillis();
                builder.code(urlResponseInfo.getHttpStatusCode());
                List<Map.Entry<String, String>> headers = urlResponseInfo.getAllHeadersAsList();
                for (Map.Entry<String, String> s : headers) {
                    String name = s.getKey();
                    String value = s.getValue();
                    builder.addHeader(name, value);
                    if (name.equalsIgnoreCase("content-type")) {
                        contentType = value;
                    }
                }
                builder.request(result);
                mutex.notifyAll();
            }
        }

        @Override
        public void onReadCompleted(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, ByteBuffer byteBuffer) throws Exception {
            synchronized (mutex) {
            Log.i(TAG,"---ReadCompleted---");

            String contentType = "text/html";

                builder.body(ResponseBody.create(MediaType.parse(contentType), byteBuffer.array()));
                builder.receivedResponseAtMillis(firstByteTime);
                builder.sentRequestAtMillis(startTime);
                response = builder.build();
                //mutex.notifyAll();
            }
        }

        @Override
        public void onSucceeded(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo) {
            synchronized (mutex) {
                Log.i(TAG, "---Succeeded---");
            }
        }

        @Override
        public void onFailed(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, CronetException e) {
            synchronized (mutex) {
                Log.i(TAG, "---Failed---");
            }
        }
    }

}
