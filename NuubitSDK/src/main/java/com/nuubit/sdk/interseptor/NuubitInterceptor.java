package com.nuubit.sdk.interseptor;

import android.util.Log;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.utils.DateTimeUtil;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealInterceptorChain;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by victor on 19.04.17.
 */

public class NuubitInterceptor implements Interceptor {
    private static final String TAG = NuubitInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response;

        long begTime = System.currentTimeMillis();
        long endTime;
        try {
            begTime = System.currentTimeMillis();
            Response resp = NuubitApplication.getInstance().getBest().send(chain);
            response = resp.newBuilder()
                    .body(new ProgressResponseBody(resp.body()))
                    .build();
            endTime = System.currentTimeMillis();
        } catch (UnknownHostException ex) {
            response = null;
            endTime = System.currentTimeMillis();
            final RequestOne statRequest = RequestOne.toRequestOne(chain.request(), chain.request(), response, NuubitApplication.getInstance().getBest().getDescription(), begTime, endTime);
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
        }
        return response;
    }
}
