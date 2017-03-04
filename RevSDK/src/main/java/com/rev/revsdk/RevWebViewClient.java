package com.rev.revsdk;

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
import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RevWebViewClient extends WebViewClient {

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
        return handleRequestViaOkHttp(url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull
            WebResourceRequest request) {
        return handleRequestViaOkHttp(request.getUrl().toString());
    }

    @NonNull
    private WebResourceResponse handleRequestViaOkHttp(@NonNull String url) {
        Response response = null;
        WebResourceResponse result = null;
        String ct = "text/html";
        String cp = "utf-8";
        try {
            final Call call = client.newCall(new Request.Builder()
                    .url(url)
                    .build()
            );

            response = call.execute();

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
        return result;
    }
}
