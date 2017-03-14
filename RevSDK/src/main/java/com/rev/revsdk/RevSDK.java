package com.rev.revsdk;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rev.revsdk.config.ConfigParamenetrs;
import com.rev.revsdk.config.ConfigsList;
import com.rev.revsdk.config.ListString;
import com.rev.revsdk.config.OperationMode;
import com.rev.revsdk.config.serialization.ConfigListDeserialize;
import com.rev.revsdk.config.serialization.ConfigListSerialize;
import com.rev.revsdk.config.serialization.ConfigParametersSerialize;
import com.rev.revsdk.config.serialization.ListStringDeserializer;
import com.rev.revsdk.config.serialization.ListStrintgSerialize;
import com.rev.revsdk.config.serialization.OperationModeDeserialize;
import com.rev.revsdk.config.serialization.OperationModeSerialize;
import com.rev.revsdk.config.serialization.TransportProtocolDeserialize;
import com.rev.revsdk.config.serialization.TransportProtocolSerialize;
import com.rev.revsdk.database.RequestTable;
import com.rev.revsdk.interseptor.RequestCreator;
import com.rev.revsdk.protocols.ListProtocol;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.statistic.Statistic;
import com.rev.revsdk.statistic.sections.AppInfo;
import com.rev.revsdk.statistic.sections.Carrier;
import com.rev.revsdk.statistic.sections.Device;
import com.rev.revsdk.statistic.sections.Event;
import com.rev.revsdk.statistic.sections.Location;
import com.rev.revsdk.statistic.sections.LogEvents;
import com.rev.revsdk.statistic.sections.Network;
import com.rev.revsdk.statistic.sections.RequestOne;
import com.rev.revsdk.statistic.sections.Requests;
import com.rev.revsdk.statistic.sections.WiFi;
import com.rev.revsdk.statistic.serialize.AppInfoDeserializer;
import com.rev.revsdk.statistic.serialize.AppInfoSerialize;
import com.rev.revsdk.statistic.serialize.CarrierDeserialize;
import com.rev.revsdk.statistic.serialize.CarrierSerialize;
import com.rev.revsdk.statistic.serialize.DeviceDeserialize;
import com.rev.revsdk.statistic.serialize.DeviceSerialize;
import com.rev.revsdk.statistic.serialize.EventSerialize;
import com.rev.revsdk.statistic.serialize.LocationDeserialize;
import com.rev.revsdk.statistic.serialize.LocationSerialize;
import com.rev.revsdk.statistic.serialize.LogEventsSerialize;
import com.rev.revsdk.statistic.serialize.NetworkDeserialize;
import com.rev.revsdk.statistic.serialize.NetworkSerialize;
import com.rev.revsdk.statistic.serialize.RequestOneDeserialize;
import com.rev.revsdk.statistic.serialize.RequestOneSerialize;
import com.rev.revsdk.statistic.serialize.RequestsDeserializer;
import com.rev.revsdk.statistic.serialize.RequestsSerialize;
import com.rev.revsdk.statistic.serialize.StatisticSerializer;
import com.rev.revsdk.statistic.serialize.WiFiDeserialize;
import com.rev.revsdk.statistic.serialize.WiFiSerialize;
import com.rev.revsdk.types.Tag;
import com.rev.revsdk.web.RevCookie;
import com.rev.revsdk.web.RevWebChromeClient;
import com.rev.revsdk.web.RevWebViewClient;

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

    public static RevWebViewClient createWebViewClient(Context context, WebView view, OkHttpClient client){
        return new RevWebViewClient(context, view, client);
    }

    public static RevWebViewClient createWebViewClient(OkHttpClient client) {
        return new RevWebViewClient(client);
    }

    public static RevWebViewClient createWebViewClient() {
        return new RevWebViewClient();
    }

    public static RevWebChromeClient createWebChromeClient(){
        return new RevWebChromeClient();
    }

    public static OkHttpClient OkHttpCreate() {
        return OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC, false, false);
    }
    public static OkHttpClient OkHttpCreate(int timeoutSec) {
        return OkHttpCreate(timeoutSec, false, false);
    }

    public static OkHttpClient OkHttpCreate(int timeoutSec, boolean followRedirect, boolean followSllRedirect) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Protocol protocol = RevApplication.getInstance().getBest();
                Response response = null;
                switch (protocol) {
                    case QUIC: {
                    }
                    case REV: {
                    }
                    default: {
                        response = standartInterceptor(chain);
                        break;
                    }
                }
                return response;
            }
        }).connectTimeout(timeoutSec, TimeUnit.SECONDS)
        .followRedirects(followRedirect)
        .followSslRedirects(followSllRedirect).cookieJar(new RevCookie());
        OkHttpClient result = httpClient.build();
        return result;
    }

    private static Response standartInterceptor(Interceptor.Chain chain) throws IOException {
        Request result = null;
        Request original = chain.request();
        boolean systemRequest = isSystem(original);
        if (!systemRequest) {
            result = processingRequest(original);
        } else {
            Log.i("System", original.toString());
            result = original;
        }

        RevApplication.getInstance().getCounter().addRequest(result, Protocol.STANDART);
        Response response = chain.proceed(result);

        if (!systemRequest && isStatistic()) {
            try {
                final RequestOne statRequest = toRequestOne(original, result, response, RevApplication.getInstance().getBest());
                Uri uri = RevApplication.getInstance().getApplicationContext().getContentResolver()
                        .insert(RequestTable.URI, RequestTable.toContentValues(RevApplication.getInstance().getConfig().getAppName(), statRequest));
                Cursor c = RevApplication.getInstance().getApplicationContext().getContentResolver()
                        .query(RequestTable.URI, null, null, null, null);
                Log.i(TAG, "Row count: " + String.valueOf(c.getCount()) + " Columns count: " + c.getColumnCount());
            } catch (NullPointerException ex) {

            }
        }
        return response;
    }

    public static Gson gsonCreate() {
        GsonBuilder gsonBuilder;

        gsonBuilder = new GsonBuilder().registerTypeAdapter(ConfigsList.class, new ConfigListDeserialize()).registerTypeAdapter(ConfigsList.class, new ConfigListSerialize())
                .registerTypeAdapter(ConfigParamenetrs.class, new ConfigListDeserialize()).registerTypeAdapter(ConfigParamenetrs.class, new ConfigParametersSerialize())
                .registerTypeAdapter(ListString.class, new ListStringDeserializer()).registerTypeAdapter(ListString.class, new ListStrintgSerialize())
                .registerTypeAdapter(ListProtocol.class, new TransportProtocolDeserialize()).registerTypeAdapter(ListProtocol.class, new TransportProtocolSerialize())
                .registerTypeAdapter(OperationMode.class, new OperationModeDeserialize()).registerTypeAdapter(OperationMode.class, new OperationModeSerialize())
                .registerTypeAdapter(RequestOne.class, new RequestOneDeserialize()).registerTypeAdapter(RequestOne.class, new RequestOneSerialize())
                .registerTypeAdapter(Requests.class, new RequestsDeserializer()).registerTypeAdapter(Requests.class, new RequestsSerialize())
                .registerTypeAdapter(Carrier.class, new CarrierSerialize()).registerTypeAdapter(Carrier.class, new CarrierDeserialize())
                .registerTypeAdapter(AppInfo.class, new AppInfoSerialize()).registerTypeAdapter(AppInfo.class, new AppInfoDeserializer())
                .registerTypeAdapter(Device.class, new DeviceSerialize()).registerTypeAdapter(Device.class, new DeviceDeserialize())
                .registerTypeAdapter(Event.class, new EventSerialize())
                .registerTypeAdapter(LogEvents.class, new LogEventsSerialize()).registerTypeAdapter(LogEvents.class, new LocationDeserialize())
                .registerTypeAdapter(Location.class, new LocationSerialize()).registerTypeAdapter(Location.class, new LocationDeserialize())
                .registerTypeAdapter(Network.class, new NetworkSerialize()).registerTypeAdapter(Network.class, new NetworkDeserialize())
                .registerTypeAdapter(Requests.class, new RequestsSerialize()).registerTypeAdapter(Requests.class, new RequestsDeserializer())
                .registerTypeAdapter(WiFi.class, new WiFiSerialize()).registerTypeAdapter(WiFi.class, new WiFiDeserialize())
                .registerTypeAdapter(Statistic.class, new StatisticSerializer());
        return gsonBuilder.create();
    }

    public static boolean isSystem(Request req) {
        boolean result = false;
        try {
            Tag tag = (Tag) req.tag();
            result = tag.getName().equals(Constants.SYSTEM_REQUEST) && ((boolean) tag.getValue());
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private static boolean isStat(Request req) {
        String statURL = RevApplication.getInstance().getConfig().getParam().get(0).getStatsReportingUrl();
        return req.url().toString().equals(statURL);
    }
    private static Request processingRequest(Request original) {
        StringBuilder sHeaders = new StringBuilder();
        for(String name : original.headers().names()){
            sHeaders.append("\n"+name);
            sHeaders.append(":");
            sHeaders.append(original.headers().get(name)+"\n");
        }
        Log.i(TAG, "Original:" + original.toString()+"\n Headers: "+sHeaders);

        RequestCreator creator = new RequestCreator(RevApplication.getInstance().getConfig());
        Request result = creator.create(original);

        sHeaders = new StringBuilder();
        for(String name : result.headers().names()){
            sHeaders.append("\n"+name);
            sHeaders.append(":");
            sHeaders.append(result.headers().get(name)+"\n");
        }

        Log.i(TAG, "Transfered: \n" + result.toString()+"\n Headers: "+sHeaders.toString());
        return result; //creator.create(original);
    }

    private static RequestOne toRequestOne(Request original, Request processed, Response response, Protocol edge_transport) {
        RequestOne result = new RequestOne();

        result.setID(-1);
        result.setConnectionID(-1);
        result.setContentEncode(getEncode(original));
        result.setContentType(getContentType(original));
        result.setStartTS(response.sentRequestAtMillis());
        result.setEndTS(response.receivedResponseAtMillis() - response.sentRequestAtMillis());
        result.setFirstByteTime(-1);
        result.setKeepAliveStatus(1);
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
        result.setXRevCache(cache == null ? Constants.UNDEFINED : cache);
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

    public static boolean isStatistic() {
        boolean result = false;
        try {
            OperationMode mode = RevApplication.getInstance().getConfig().getParam().get(0).getOperationMode();
            switch (mode) {
                case transfer_and_report: {
                    result = true;
                    break;
                }
                case transfer_only: {
                    result = false;
                    break;
                }
                case report_only: {
                    result = true;
                    break;
                }
                case off: {
                    result = false;
                    break;
                }
            }
        } catch (NullPointerException ex) {
            result = false;
        }
        Log.i("System", String.valueOf(result));
        return result;
    }

}
