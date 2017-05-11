package com.nuubit.sdk.web;

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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.interseptor.ProgressResponseBody;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;

import org.acra.util.IOUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Header;
import okio.BufferedSource;

import static com.nuubit.sdk.utils.IOUtils.requestSize;


public class NuubitWebViewClient extends WebViewClient {

    private static final String TAG = NuubitWebViewClient.class.getSimpleName();
    private Context context;
    private WebView view;
    private OkHttpClient client;
    private boolean isFullResponse;

    private String sData;

    private OnURLChanged listener;
    //private long startLoad;
    //private long finishLoad;
    private long sentBytes;
    private long receivedBytes;

    private boolean isOrigin = false;
    public NuubitWebViewClient(Context context, WebView view, OkHttpClient client) {
        super();
        this.context = context;
        this.view = view;
        this.client = client;
        this.isFullResponse = false;
        this.sentBytes = 0;
        this.receivedBytes = 0;

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        view.getSettings().setPluginState(WebSettings.PluginState.ON);
    }

    public NuubitWebViewClient(OkHttpClient client) {
        this(null, null, client);
    }

    public NuubitWebViewClient() {
        this(NuubitSDK.OkHttpCreate());
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.sentBytes = 0;
        this.receivedBytes = 0;
        String url_new = view.getUrl();
        if (listener != null && url_new != null && !url_new.isEmpty()) {
            listener.onURLChanged(url_new);
        }
        //startLoad = System.currentTimeMillis();
        Log.d("WEBVIEWDATA","Started: "+url+"  "+view.getUrl());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        String url_new = view.getUrl();
        if (listener != null && url_new != null && !url_new.isEmpty()) {
            listener.onURLChanged(url_new);
        }
        //finishLoad = System.currentTimeMillis();
        Log.d("WEBVIEWDATA","Finshed: "+url+"  "+view.getUrl());
    }

    @Override
    public void onPageCommitVisible(WebView view, String url){
        super.onPageCommitVisible(view,url);
    }
/*
    public void setOnLoadedPage(OnLoaded loaded) {
        this.loaded = loaded;
    }
*/
    public void setOnURLChangeListener(OnURLChanged listener) {
        this.listener = listener;
    }

    public void onURLChanged(String newURL) {
        if (listener != null && !newURL.isEmpty()) {
            listener.onURLChanged(newURL);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull String url) {
        WebResourceRequestDefault request = new WebResourceRequestDefault(Uri.parse(url));
        return handleRequest(request);
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull WebResourceRequest request) {
        return handleRequest(request);
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("NewApi")
    @NonNull
    private WebResourceResponse handleRequest(WebResourceRequest aRequest) {
        int redirectCounter = 0;
        String url = aRequest.getUrl().toString();
        String method = aRequest.getMethod();
        Headers.Builder hBuilder = new Headers.Builder();
        for (String key : aRequest.getRequestHeaders().keySet()) {
            hBuilder.add(key, aRequest.getRequestHeaders().get(key));
        }
        RequestBody body = null;
        Log.i(TAG, url);
        Response response = null;
        WebResourceResponse result = null;

        String ct = aRequest.getRequestHeaders().get("content-type") == null ? "text/html" :
                aRequest.getRequestHeaders().get("content-type");
        String cp = "utf-8";

        final String fct = ct;
        if (body == null && method.equalsIgnoreCase("POST")) {
            body = RequestBody.create(MediaType.parse(ct), sData == null ? "" : sData);
        }
        try {
            HTTPCode code = HTTPCode.UNDEFINED;
            Request request;
            if(isOrigin()) {
                request = new Request.Builder()
                        .url(url)
                        .headers(hBuilder.build())
                        .method(method, body)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(url)
                        .headers(hBuilder.build())
                        .method(method, body)
                        .tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true))
                        .build();
            }
            this.sentBytes += requestSize(request.newBuilder().build());
            Response resp = null;

            //this.startLoad = System.currentTimeMillis();

            while (code.getType() != HTTPCode.Type.SUCCESSFULL && redirectCounter < NuubitConstants.MAX_REDIRECT) {
                final Call call = client.newCall(request);
                resp = call.execute();
                code = HTTPCode.create(resp.code());
                if(isFullResponse && code.getType() == HTTPCode.Type.SUCCESSFULL) {
                    response = resp.newBuilder().body(new ProgressResponseBody(resp.body(), pb, resp)).build();
                    long headersSize = com.nuubit.sdk.utils.IOUtils.responseHeadersSize(response);
                    this.receivedBytes += headersSize;
                    Log.i("WEBVIEWDATA", "Headers size: "+headersSize);

                } else{
                    response = resp;
                }


                if ((code == HTTPCode.MOVED_PERMANENTLY) || (code == HTTPCode.FOUND)) {
                    url = response.header("location");
                    code = HTTPCode.create(response.code());
                    if(isOrigin())
                    request = new Request.Builder()
                            .url(url)
                            .headers(hBuilder.build())
                            .method(method, body)
                            .build();
                    else{
                        request = new Request.Builder()
                                .url(url)
                                .headers(hBuilder.build())
                                .tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true))
                                .method(method, body)
                                .build();
                    }
                    redirectCounter++;
                }

                if (code.getType() == HTTPCode.Type.CLIENT_ERROR) break;

                if (code.getType() == HTTPCode.Type.SUCCESSFULL)
                    Log.i("Code", "In cycle: " + code.toString());
                else
                    Log.i("Error", "In cycle: " + code.toString());

            }
            if (code.getType() == HTTPCode.Type.SUCCESSFULL)
                Log.i("Code", "Cycle out: " + code.toString());
            else
                Log.i("Error", "Cycle out: " + code.toString());

            if (response != null) {
                String header = response.header("content-type");
                if (header != null) {
                    String[] ss = header.split(";");
                    switch (ss.length) {
                        case 1: {
                            ct = ss[0];
                            break;
                        }
                        case 2: {
                            ct = ss[0];
                            String[] s = ss[1].split("=");
                            cp = s[1];
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            result = new WebResourceResponse(
                    response.header("content-type", ct),
                    response.header("content-encoding", cp),
                    response.body().byteStream());
        } catch (NullPointerException ex) {
            String s = "<html><body>" + ex.getMessage() + "</body></html>";
            InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            result = new WebResourceResponse(ct, cp, stream);
        }
        result.setMimeType(ct);
        result.setEncoding(cp);
        return result;
    }

    public void setOrigin(boolean origin){
        this.isOrigin = origin;
    }
    public boolean isOrigin(){
        return isOrigin;
    }
    public void setFullResponse(boolean full){this.isFullResponse = full;}
    public boolean isFullResponse(){return this.isFullResponse;}
/*
    public long getStartLoad() {
        return startLoad;
    }

    public long getFinishLoad() {
        return finishLoad;
    }
*/
    public long getSentBytes() {
        return sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    private ProgressResponseBody.OnLoadedBody pb = new ProgressResponseBody.OnLoadedBody() {
        @Override
        public void onLoaded(long size) {
            Log.i("WEBVIEWDATA", "Size body: "+size);
            NuubitWebViewClient.this.receivedBytes += size;
        }
    };

    public interface OnURLChanged {
        void onURLChanged(String url);
    }
}

