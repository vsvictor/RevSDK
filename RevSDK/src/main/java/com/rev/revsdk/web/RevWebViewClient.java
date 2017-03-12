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
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rev.revsdk.RevSDK;
import com.rev.revsdk.types.HTTPCode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RevWebViewClient extends WebViewClient {

    private static final String TAG = RevWebViewClient.class.getSimpleName();
    private ProgressDialog progressDialog;
    private OkHttpClient client;

    public RevWebViewClient(OkHttpClient client) {
        super();
        this.client = client;
    }

    public RevWebViewClient() {
        this(RevSDK.OkHttpCreate());
    }

    @SuppressWarnings("deprecation")
    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull String url) {
        return handleRequest(url, "GET", null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull WebResourceRequest request) {
        return handleRequest(request.getUrl().toString(), request.getMethod(), null);
    }

    @NonNull
    private WebResourceResponse handleRequest(@NonNull String url, String method, RequestBody aBody) {
        Log.i(TAG, url);
        Response response = null;
        WebResourceResponse result = null;
        String ct = "text/html";
        String cp = "utf-8";
        RequestBody body = aBody;
        final String fct = ct;
        if (body == null && method.equalsIgnoreCase("POST")) {
            body = RequestBody.create(null, new byte[0]);
        }
        try {
            HTTPCode code = HTTPCode.UNDEFINED;
            while (code != HTTPCode.OK) {
                final Call call = client.newCall(new Request.Builder()
                        .url(url)
                        .method(method, body)
                        .build()
                );

                response = call.execute();
                String location = "";
                code = HTTPCode.create(response.code());
                if ((code == HTTPCode.MOVED_PERMANENTLY) ||
                        (code == HTTPCode.FOUND)) {
                    //location = response.header("location");
                    //response = runGetRequest(location);
                    url = response.header("location");
                    code = HTTPCode.create(response.code());
                    continue;
                }
                if (code.getType() == HTTPCode.Type.CLIENT_ERROR) {
                    //response = runGetRequest(response.request().url().toString());
                    url = response.request().url().toString();
                    code = HTTPCode.create(response.code());
                    continue;
                }
            }
            Log.i(TAG, response.toString());
            String header = response.header("content-type");
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

    private Response runGetRequest(String url) {
        Response response;
        Request req = new Request.Builder()
                .url(url)
                .build();
        try {
            response = client.newCall(req).execute();
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        }
        return response;
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
