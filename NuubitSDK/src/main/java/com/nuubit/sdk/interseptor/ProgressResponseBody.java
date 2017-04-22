package com.nuubit.sdk.interseptor;

import java.io.IOException;

import okhttp3.MediaType;
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
    private BufferedSource bufferedSource;
    private boolean firstStep = true;
    private long firstByteTime;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }
    public ProgressResponseBody(ResponseBody responseBody) {
        this(responseBody, null);
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
                    firstStep = false;
                }
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if(progressListener != null) {
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    progressListener.lastByteTime(System.currentTimeMillis());
                }
                return bytesRead;
            }
        };
    }
    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
        void firstByteTime(long time);
        void lastByteTime(long time);
    }
}
