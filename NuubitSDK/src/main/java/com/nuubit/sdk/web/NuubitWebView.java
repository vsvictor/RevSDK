package com.nuubit.sdk.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Map;

/**
 * Created by victor on 10.05.17.
 */

public class NuubitWebView extends WebView {

    private long startLoad = 0;
    private long finishLoad = 0;
    private OnLoaded loader;
    private WebViewClient webClient;

    public NuubitWebView(Context context) {
        super(context);
    }

    public NuubitWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NuubitWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public NuubitWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NuubitWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        startLoad = System.currentTimeMillis();
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        startLoad = System.currentTimeMillis();
        super.loadUrl(url);
    }

    @Override
    public void postUrl(String url, byte[] postData) {
        startLoad = System.currentTimeMillis();
        super.postUrl(url, postData);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        startLoad = System.currentTimeMillis();
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        startLoad = System.currentTimeMillis();
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public void setOnLoadedPage(OnLoaded loader) {
        this.loader = loader;
    }

    @Override
    public void setWebViewClient(WebViewClient webClient) {
        super.setWebViewClient(webClient);
        this.webClient = webClient;
    }

    public WebViewClient getWebClient() {
        return this.webClient;
    }

    public void loaded() {
        if (loader != null) {
            long start = this.startLoad;
            long finish = System.currentTimeMillis();
            long sent = 0;
            long received = 0;
            if (this.webClient != null) {
                NuubitWebViewClient web = (NuubitWebViewClient) this.webClient;
                sent = web.getSentBytes();
                received = web.getReceivedBytes();
            }
            loader.onLoaded(200, this.getUrl(), start, finish, sent, received);
        }
    }

    public interface OnLoaded {
        void onLoaded(int httpCode, String url, long startTime, long finishTime, long sent, long received);
    }

}
