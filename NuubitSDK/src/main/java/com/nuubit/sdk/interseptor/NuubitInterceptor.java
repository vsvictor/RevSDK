package com.nuubit.sdk.interseptor;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.sections.RequestOne;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealInterceptorChain;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by victor on 19.04.17.
 */

public class NuubitInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        RealInterceptorChain realChain = (RealInterceptorChain) chain;
        HttpCodec httpCodec = realChain.httpStream();
        StreamAllocation streamAllocation = realChain.streamAllocation();
        RealConnection connection = (RealConnection) realChain.connection();
        Request request = realChain.request();

        long sentRequestMillis = System.currentTimeMillis();
        httpCodec.writeRequestHeaders(request);

        Response.Builder responseBuilder = null;
        if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
            if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
                httpCodec.flushRequest();
                responseBuilder = httpCodec.readResponseHeaders(true);
            }
            if (responseBuilder == null) {
                Sink requestBodyOut = httpCodec.createRequestBody(request, request.body().contentLength());
                BufferedSink bufferedRequestBody = Okio.buffer(requestBodyOut);
                request.body().writeTo(bufferedRequestBody);
                bufferedRequestBody.close();
            } else if (!connection.isMultiplexed()) {
                streamAllocation.noNewStreams();
            }
        }
        httpCodec.finishRequest();
        if (responseBuilder == null) {
            responseBuilder = httpCodec.readResponseHeaders(false);
        }

        Response response = responseBuilder
                .request(request)
                .handshake(streamAllocation.connection().handshake())
                .sentRequestAtMillis(sentRequestMillis)
                .receivedResponseAtMillis(System.currentTimeMillis())
                .build();

        int code = response.code();
        response = response.newBuilder()
                .body(httpCodec.openResponseBody(response))
                .build();
        if ("close".equalsIgnoreCase(response.request().header("Connection"))
                || "close".equalsIgnoreCase(response.header("Connection"))) {
            streamAllocation.noNewStreams();
        }

        if ((code == 204 || code == 205) && response.body().contentLength() > 0) {
            throw new ProtocolException(
                    "HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
        }



        long begTime = System.currentTimeMillis();
        long endTime;
        try {
            begTime = System.currentTimeMillis();
            response = NuubitApplication.getInstance().getBest().send(chain);
        } catch (UnknownHostException ex) {
            response = null;
            endTime = System.currentTimeMillis();
            final RequestOne statRequest = RequestOne.toRequestOne(chain.request(), chain.request(), response, NuubitApplication.getInstance().getBest().getDescription(), begTime, endTime);
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));

        }
        return response;
    }
}
