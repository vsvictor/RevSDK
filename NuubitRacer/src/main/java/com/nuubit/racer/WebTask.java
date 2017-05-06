package com.nuubit.racer;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import android.webkit.WebView;

import com.nuubit.racer.model.Row;
import com.nuubit.racer.model.Table;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.web.NuubitWebViewClient;

import okhttp3.OkHttpClient;

/**
 * Created by victor on 07.05.17.
 */

public class WebTask {
    private Context context;
    private String url;
    private String source;
    private OkHttpClient client;
    private Table table;
    private ParalellyWebActivity.ResultAdapter adapter;
    private WebView web;
    private boolean isOrigin;
    private ProgressDialog dialog;
    public WebTask(Context context, String url, String souirce, OkHttpClient client, Table table, ParalellyWebActivity.ResultAdapter adapter, boolean isOrigin){
        this.context = context;
        this.url = url;
        this.client = client;
        this.table = table;
        this.source = source;
        this.adapter = adapter;
        this.isOrigin = isOrigin;
        this.dialog = null;

    }
    public WebTask(Context context, String url, String source, OkHttpClient client, Table table, ParalellyWebActivity.ResultAdapter adapter, boolean isOrigin, ProgressDialog dialog){
        this(context,url,source,client,table,adapter, isOrigin);
        this.dialog = dialog;
    }

    public void start(){
        web = new WebView(context);
        final NuubitWebViewClient webClient = NuubitSDK.createWebViewClient(context, web, client);
        webClient.setOrigin(this.isOrigin);
        web.setWebViewClient(webClient);
        web.setWebChromeClient(NuubitSDK.createWebChromeClient());
        webClient.setOnLoadedPage(new NuubitWebViewClient.OnLoaded() {
            @Override
            public void onLoaded(long startTime, long finishTime, long sent, long received) {
                Log.i("WEBVIEWDATA", "Start: "+startTime+" stop: "+finishTime+" sent: "+sent+" received: "+received+" Time: "+(finishTime-startTime));
                Row row = new Row();
                row.setUrl(url);
                row.setBody(sent);
                row.setFinish(finishTime);
                row.setPayload(received);
                row.setSource(source);
                row.setStart(startTime);
                table.add(row);
                adapter.dataUpdated();
                if(dialog != null){
                    dialog.incrementProgressBy(1);
                }
            }
        });
        web.loadUrl(url);
    }
}
