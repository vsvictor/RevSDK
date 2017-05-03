package com.nuubit.sdk.web;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.webkit.WebResourceRequest;

import java.util.HashMap;
import java.util.Map;

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

@SuppressLint("NewApi")
public class WebResourceRequestDefault implements WebResourceRequest {
    private Uri url;
    private String method = "GET";
    private Map<String, String> headers;
    public WebResourceRequestDefault(Uri url){
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
