package com.nuubit.racer.web;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nuubit.sdk.web.NuubitWebViewClient;

/**
 * Created by victor on 14.05.17.
 */

public class WebViewClientCounter extends NuubitWebViewClient {
    private int count = 0;
    @SuppressLint("NewApi")
    @NonNull
    private WebResourceResponse handleRequest(WebResourceRequest aRequest) {
        count++;
        return null;
    }
    public int getCountRequest(){
        return count;
    }
}
