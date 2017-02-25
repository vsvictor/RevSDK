package com.rev.revsdk;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.rev.revsdk.database.RequestTable;
import com.rev.revsdk.interseptor.RequestCreator;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.statistic.sections.RequestOne;
import com.rev.revsdk.utils.Tag;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

public class RevSDK {
    private static final String TAG = RevSDK.class.getSimpleName();

    public static RevWebViewClient createWebViewClient(OkHttpClient client){
        return new RevWebViewClient();
    }
    public static RevWebViewClient createWebViewClient(){
        return new RevWebViewClient();
    }
    public static OkHttpClient OkHttpCreate() {
        return OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC);
    }

    public static OkHttpClient OkHttpCreate(int timeoutSec) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request result = null;
                Request original = chain.request();
                boolean systemRequest = isSystem(original);
                Log.i(TAG, "is System?" + String.valueOf(systemRequest) + " ,Intercepted: \n" + original.toString());
                if (!systemRequest) {
                    result = processingRequest(original);
                    int i= 0;
                }
                else result = original;

                Log.i(TAG, "\n"+result.toString());

                Response response = chain.proceed(result);

                if (!systemRequest) {

                    final RequestOne statRequest = toRequestOne(original, result, response, RevApplication.getInstance().getBest());
                    Uri uri = RevApplication.getInstance().getApplicationContext().getContentResolver()
                            .insert(RequestTable.URI, RequestTable.toContentValues(RevApplication.getInstance().getConfig().getAppName(), statRequest));
                    Log.i(TAG, "Inserted to URI: " + uri.toString());

                    Cursor c = RevApplication.getInstance().getApplicationContext().getContentResolver()
                            .query(RequestTable.URI,null,null, null,null);
                    Log.i(TAG, "Row count: "+String.valueOf(c.getCount())+" Columns count: "+c.getColumnCount());
                }
                return response;
            }
        }).connectTimeout(timeoutSec, TimeUnit.SECONDS);
        return httpClient.build();
    }

    private static boolean isSystem(Request req) {
        boolean result = false;
        try {
            Tag tag = (Tag) req.tag();
            result = (boolean) tag.getValue();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private static Request processingRequest(Request original) {
        RequestCreator creator = new RequestCreator(RevApplication.getInstance().getConfig());
        Log.i(TAG, "Intercepted: \n" + original.toString());
        return creator.create(original);
    }

    private static RequestOne toRequestOne(Request original, Request processed, Response response, Protocol edge_transport) {
        RequestOne result = new RequestOne();

        result.setID(-1);
        result.setConnectionID(-1);
        result.setContentEncode(getEncode(original));
        result.setContentType(getContentType(original));
        result.setStartTS(response.sentRequestAtMillis());
        result.setEndTS(response.receivedResponseAtMillis()-response.sentRequestAtMillis());
        result.setFirstByteTime(-1);
        result.setKeepAliveStatus(1); //TODO how
        result.setLocalCacheStatus(response.cacheControl().toString());
        result.setMethod(original.method());
        result.setEdgeTransport(edge_transport);

        //result.setNetwork(NetworkUtil.getNetworkName(RevApplication.getInstance()));
        result.setNetwork("NETWORK");

        result.setProtocol(Protocol.fromString(original.isHttps() ? "https" : "http"));
        result.setReceivedBytes(response.body().contentLength());
        RequestBody body = original.body();
        if (body != null) {
            try {
                result.setSentBytes(body.contentLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else result.setSentBytes(0);
        result.setStatusCode(response.code());
        result.setSuccessStatus(response.code());
        result.setTransportProtocol(Protocol.STANDART);
        result.setURL(original.url().toString());
        result.setDestination(original == processed ? "origin" : "rev_edge");
        String cache = response.header("x-rev-cache");
        result.setXRevCache(cache==null?Constants.undefined:cache);
        result.setDomain(original.url().host());

        return result;
    }

    private static String getEncode(Request req) {
        String result = "ISO-8859-1";
        String header = req.header("Content-Type");
        if (header == null || header.isEmpty()) return result;
        String[] sp = header.split(";");
        if (sp.length > 1 && !sp[1].isEmpty()) result = sp[1];
        return result;
    }

    private static String getContentType(Request req) {
        String result = "text/html";
        String header = req.header("Content-Type");
        if (header == null || header.isEmpty()) return result;
        String[] sp = header.split(";");
        if (sp.length > 0 && !sp[0].isEmpty()) result = sp[0];
        return result;
    }

}
