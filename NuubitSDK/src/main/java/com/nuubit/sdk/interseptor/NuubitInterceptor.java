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
            response = NuubitApplication.getInstance().getBest().send(chain);
        } catch (UnknownHostException ex) {
            response = null;
            endTime = System.currentTimeMillis();
            final RequestOne statRequest = RequestOne.toRequestOne(chain.request(), chain.request(), response, NuubitApplication.getInstance().getBest().getDescription(), begTime, endTime);
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
        }

        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(), progressListener))
                .build();
        //return response;
    }

    final ProgressListener progressListener = new ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
            //Log.i(TAG, String.valueOf(bytesRead));
            //Log.i(TAG, String.valueOf(contentLength));
            //Log.i(TAG, String.valueOf(done));
            //Log.i(TAG, String.format("%l%% done\n", (100 * bytesRead) / contentLength));
        }
        @Override
        public void firstByteTime(long time){
            Log.i(TAG, DateTimeUtil.longDateToString(NuubitApplication.getInstance().getApplicationContext(), time));
            Log.i(TAG, "First byte: "+String.valueOf(time));
        }
        @Override
        public void lastByteTime(long time){
            Log.i(TAG, DateTimeUtil.longDateToString(NuubitApplication.getInstance().getApplicationContext(), time));
            Log.i(TAG, "Last byte: "+String.valueOf(time));
        }

    };

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;
        private boolean firstStep = true;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;
                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    if(firstStep && byteCount>0){
                        progressListener.firstByteTime(System.currentTimeMillis());
                        firstStep = false;
                    }
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    progressListener.lastByteTime(System.currentTimeMillis());
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
        void firstByteTime(long time);
        void lastByteTime(long time);
    }
}
