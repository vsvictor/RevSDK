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
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.types.HTTPCode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NuubitWebViewClient extends WebViewClient {

    private static final String TAG = NuubitWebViewClient.class.getSimpleName();
    private Context context;
    private WebView view;
    private OkHttpClient client;

    private String sData;

    private OnURLChanged listener;
    private OnTimes timeListener;

    private long startLoad;
    private long finishLoad;
/*
    private class JavaScriptInterface {
        private final String TAG = JavaScriptInterface.class.getSimpleName();
        @JavascriptInterface
        public void processHTML(String formData) {
            Log.d(TAG, "form data: " + formData);
            sData = formData;
        }
    }
*/

    public NuubitWebViewClient(Context context, WebView view, OkHttpClient client) {
        super();
        this.context = context;
        this.view = view;
        this.client = client;

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        view.getSettings().setPluginState(WebSettings.PluginState.ON);
//        view.addJavascriptInterface(new JavaScriptInterface(), "AndroidInterface");
    }

    public NuubitWebViewClient(OkHttpClient client) {
        this(null, null, client);
    }

    public NuubitWebViewClient() {
        this(NuubitSDK.OkHttpCreate());
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon){
        super.onPageStarted(view, url, favicon);
        String url_new = view.getUrl();
        if(listener != null && url_new != null && !url_new.isEmpty()){
            listener.onURLChanged(url_new);
        }
        startLoad = System.currentTimeMillis();
        if(timeListener != null) {
            timeListener.onStart(url, startLoad);
        }
    }
    @Override
    public void onPageFinished(WebView view, String url){
        super.onPageFinished(view, url);
        String url_new = view.getUrl();
        if(listener != null && url_new != null && !url_new.isEmpty()){
            listener.onURLChanged(url_new);
        }
        finishLoad = System.currentTimeMillis();
        if(timeListener != null){
            timeListener.onStop(url, finishLoad);
            timeListener.onStartStop(url, startLoad, finishLoad);
        }
    }

    public void setOnURLChangeListener(OnURLChanged listener){
        this.listener = listener;
    }
    public void onURLChanged(String newURL){
        if(listener != null && !newURL.isEmpty()){
            listener.onURLChanged(newURL);
        }
    }
    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull String url) {
        WebResourсeRequestDefault request = new WebResourсeRequestDefault(Uri.parse(url));
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
        for(String key : aRequest.getRequestHeaders().keySet()){
            hBuilder.add(key, aRequest.getRequestHeaders().get(key));
        }
        RequestBody body = null;
        Log.i(TAG, url);
        Response response = null;
        WebResourceResponse result = null;

        String ct = aRequest.getRequestHeaders().get("content-type")==null?"text/html":
                aRequest.getRequestHeaders().get("content-type");
        String cp = "utf-8";

        final String fct = ct;
        if (body == null && method.equalsIgnoreCase("POST")) {
            body = RequestBody.create(MediaType.parse(ct), sData==null?"":sData);
        }
        try {
            HTTPCode code = HTTPCode.UNDEFINED;
            Request request = new Request.Builder()
                    .url(url)
                    .headers(hBuilder.build())
                    .method(method, body)
                    .build();

            while (code.getType() != HTTPCode.Type.SUCCESSFULL && redirectCounter < NuubitConstants.MAX_REDIRECT) {
                final Call call = client.newCall(request);
                response = call.execute();
                code = HTTPCode.create(response.code());
                if ((code == HTTPCode.MOVED_PERMANENTLY) || (code == HTTPCode.FOUND)) {
                    url = response.header("location");
                    code = HTTPCode.create(response.code());
                    request = new Request.Builder()
                            .url(url)
                            .headers(hBuilder.build())
                            .method(method, body)
                            .build();
                    redirectCounter++;
                }
                if(code.getType() == HTTPCode.Type.CLIENT_ERROR) break;

                if(code.getType() == HTTPCode.Type.SUCCESSFULL)
                    Log.i("Code", "In cycle: " + code.toString());
                else
                    Log.i("Error", "In cycle: " + code.toString());

            }

            if(code.getType() == HTTPCode.Type.SUCCESSFULL)
                Log.i("Code", "Cycle out: "+code.toString());
            else
                Log.i("Error", "Cycle out: "+code.toString());

            if(response != null) {
                String header = response.header("content-type");
                if(header != null) {
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
            result = null;
        }
        result.setMimeType(ct);
        result.setEncoding(cp);
        return result;
    }

    public void setOnTimeListener(OnTimes listener){
        this.timeListener = listener;
    }

    public interface OnURLChanged {
        void onURLChanged(String url);
    }
    public interface OnTimes{
        void onStart(String url, long start);
        void onStop(String url, long stop);
        void onStartStop(String url, long start, long stop);
    }
}

