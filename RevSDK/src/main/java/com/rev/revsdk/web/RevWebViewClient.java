package com.rev.revsdk.web;

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

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rev.revsdk.Constants;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.types.HTTPCode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.framed.Header;

public class RevWebViewClient extends WebViewClient {

    private static final String TAG = RevWebViewClient.class.getSimpleName();
    private Context context;
    private WebView view;
    private OkHttpClient client;

    public RevWebViewClient(Context context, WebView view, OkHttpClient client) {
        super();
        this.context = context;
        this.view = view;
        this.client = client;
    }

    public RevWebViewClient(OkHttpClient client) {
        this(null, null, client);
    }

    public RevWebViewClient() {
        this(RevSDK.OkHttpCreate());
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull String url) {
        WebResourсeRequestDefault request = new WebResourсeRequestDefault(Uri.parse(url));
        return handleRequest(request);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull WebResourceRequest request) {
        WebResourceRequest aReq = request;
        return handleRequest(aReq);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
        String ct = "text/html";
        String cp = "utf-8";
        final String fct = ct;
        if (body == null && method.equalsIgnoreCase("POST")) {
            body = RequestBody.create(null, new byte[0]);
        }
        try {
            HTTPCode code = HTTPCode.UNDEFINED;
            Request request = new Request.Builder()
                    .url(url)
                    .headers(hBuilder.build())
                    .method(method, body)
                    .build();

            while (code.getType() != HTTPCode.Type.SUCCESSFULL && redirectCounter< Constants.MAX_REDIRECT) {
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
            //String ss = new String(response.body().bytes());
            result = new WebResourceResponse(
                    response.header("content-type", ct),
                    response.header("content-encoding", cp),
                    response.body().byteStream());
        } catch (NullPointerException ex) {
            result = null;
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/
        result.setMimeType(ct);
        result.setEncoding(cp);
        return result;
    }

    private PostInterceptor.FormRequestContents mNextFormRequestContents = null;

    public void nextMessageIsFormRequest(PostInterceptor.FormRequestContents formRequestContents) {
        mNextFormRequestContents = formRequestContents;
    }

    private PostInterceptor.AjaxRequestContents mNextAjaxRequestContents = null;

    public void nextMessageIsAjaxRequest(PostInterceptor.AjaxRequestContents ajaxRequestContents) {
        mNextAjaxRequestContents = ajaxRequestContents;
    }
}
