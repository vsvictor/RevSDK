package com.rev.sdk.web;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.webkit.WebResourceRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by victor on 13.03.17.
 */

@SuppressLint("NewApi")
public class WebResourсeRequestDefault implements WebResourceRequest {
    private Uri url;
    private String method = "GET";
    private Map<String, String> headers;
    public WebResourсeRequestDefault(Uri url){
        super();
        this.url = url;
        this.headers = new HashMap<String, String>();
    }
    @Override
    public Uri getUrl() {
        return url;
    }

    @Override
    public boolean isForMainFrame() {
        return false;
    }

    @Override
    public boolean isRedirect() {
        return false;
    }

    @Override
    public boolean hasGesture() {
        return false;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        return headers;
    }
}
