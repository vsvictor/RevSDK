package com.nuubit.sdk.interseptor;

import android.util.Log;

import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.web.NuubitWebViewClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by victor on 23.04.17.
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private ProgressListener progressListener;
    private OnLoadedBody loaded;
    private BufferedSource bufferedSource;
    private boolean firstStep = true;
    private boolean lastStep = false;
    private long firstByteTime;
    private long lastByteTime;
    private RequestOne req;
    private Response response;
    private boolean isServ = false;
    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
        this.req = null;
    }
    public ProgressResponseBody(ResponseBody responseBody, OnLoadedBody loaded, Response response) {
        this.response = response;
        this.responseBody = responseBody;
        this.progressListener = null;
        this.req = null;
        this.loaded = loaded;
    }

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener, RequestOne req) {
        this.responseBody = responseBody;
        this.progressListener = listener;
        this.req = req;
        //Log.i("REQESTONE", "Response body created");
    }

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener, Response response, boolean serv) {
        this.responseBody = responseBody;
        this.progressListener = listener;
        this.req = null;
        this.response = response;
        this.isServ = serv;
        //Log.i("REQESTONE", "Response body created");
    }

    public ProgressResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
        this.progressListener = null;
        this.req = null;
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

    public void setOnProgressListener(ProgressListener listener){
        this.progressListener = listener;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                if(firstStep && byteCount>0){
                    firstByteTime = System.currentTimeMillis();
                    if(progressListener != null) {
                        progressListener.firstByteTime(firstByteTime);
                    }
                    if(req != null){
                        req.setFirstByteTime(firstByteTime);
                    }
                    firstStep = false;
                }
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if(progressListener != null) {
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }
                if((req != null || response != null) && progressListener != null && bytesRead == -1 && !firstStep && !lastStep){
                    lastByteTime = System.currentTimeMillis();
                    req.setEndTS(lastByteTime);
                    req.setReceivedBytes(totalBytesRead);
                    progressListener.onRequest(req);
                    progressListener.lastByteTime(lastByteTime);
                    progressListener.onResponse(response,isServ, lastByteTime);
                    //Log.i("REQESTONE", "Read "+String.valueOf(bytesRead));

                    lastStep = true;
                }
                if(response != null && loaded != null && bytesRead == -1 && !firstStep && !lastStep){
                    if(loaded != null){
                        HTTPCode code = HTTPCode.create(response.code());
                        if(code.getType() == HTTPCode.Type.SUCCESSFULL) {
                            loaded.onLoaded(totalBytesRead);
                            //Log.i("WEBVIEWDATA", "ProgressResponseBody size: " + totalBytesRead);
                        }
                        lastStep = true;
                    }
                }
                return bytesRead;
            }
        };
    }
    public long getFirstByteTime(){
        return firstByteTime;
    }
    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
        void firstByteTime(long time);
        void lastByteTime(long time);
        void onRequest(RequestOne res);
        void onResponse(Response response, boolean serv, long lastByteTime);
    }
    public interface OnLoadedBody{
        void onLoaded(long size);
    }
}
