package com.nuubit.sdk;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nuubit.sdk.config.ConfigParamenetrs;
import com.nuubit.sdk.config.ConfigsList;
import com.nuubit.sdk.config.ListString;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.config.serialization.ConfigListDeserialize;
import com.nuubit.sdk.config.serialization.ConfigListSerialize;
import com.nuubit.sdk.config.serialization.ConfigParametersSerialize;
import com.nuubit.sdk.config.serialization.ListStringDeserializer;
import com.nuubit.sdk.config.serialization.ListStrintgSerialize;
import com.nuubit.sdk.config.serialization.OperationModeDeserialize;
import com.nuubit.sdk.config.serialization.OperationModeSerialize;
import com.nuubit.sdk.config.serialization.TransportProtocolDeserialize;
import com.nuubit.sdk.config.serialization.TransportProtocolSerialize;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.interseptor.NuubitInterceptor;
import com.nuubit.sdk.interseptor.RequestCreator;
import com.nuubit.sdk.protocols.ListProtocol;
import com.nuubit.sdk.statistic.Statistic;
import com.nuubit.sdk.statistic.sections.AppInfo;
import com.nuubit.sdk.statistic.sections.Carrier;
import com.nuubit.sdk.statistic.sections.Device;
import com.nuubit.sdk.statistic.sections.Location;
import com.nuubit.sdk.statistic.sections.LogEvent;
import com.nuubit.sdk.statistic.sections.LogEvents;
import com.nuubit.sdk.statistic.sections.Network;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.statistic.sections.Requests;
import com.nuubit.sdk.statistic.sections.WiFi;
import com.nuubit.sdk.statistic.serialize.AppInfoDeserializer;
import com.nuubit.sdk.statistic.serialize.AppInfoSerialize;
import com.nuubit.sdk.statistic.serialize.CarrierDeserialize;
import com.nuubit.sdk.statistic.serialize.CarrierSerialize;
import com.nuubit.sdk.statistic.serialize.DeviceDeserialize;
import com.nuubit.sdk.statistic.serialize.DeviceSerialize;
import com.nuubit.sdk.statistic.serialize.EventSerialize;
import com.nuubit.sdk.statistic.serialize.LocationDeserialize;
import com.nuubit.sdk.statistic.serialize.LocationSerialize;
import com.nuubit.sdk.statistic.serialize.LogEventsSerialize;
import com.nuubit.sdk.statistic.serialize.NetworkDeserialize;
import com.nuubit.sdk.statistic.serialize.NetworkSerialize;
import com.nuubit.sdk.statistic.serialize.RequestOneDeserialize;
import com.nuubit.sdk.statistic.serialize.RequestOneSerialize;
import com.nuubit.sdk.statistic.serialize.RequestsDeserializer;
import com.nuubit.sdk.statistic.serialize.RequestsSerialize;
import com.nuubit.sdk.statistic.serialize.StatisticSerializer;
import com.nuubit.sdk.statistic.serialize.WiFiDeserialize;
import com.nuubit.sdk.statistic.serialize.WiFiSerialize;
import com.nuubit.sdk.types.Tag;
import com.nuubit.sdk.web.NuubitCookie;
import com.nuubit.sdk.web.NuubitWebChromeClient;
import com.nuubit.sdk.web.NuubitWebViewClient;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.ConnectInterceptor;

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

public class NuubitSDK {
    private static final String TAG = NuubitSDK.class.getSimpleName();
    private static OkHttpClient client = null;

    public static NuubitWebViewClient createWebViewClient(Context context, WebView view, OkHttpClient client) {
        return new NuubitWebViewClient(context, view, client);
    }

    public static NuubitWebViewClient createWebViewClient(OkHttpClient client) {
        return new NuubitWebViewClient(client);
    }

    public static NuubitWebViewClient createWebViewClient() {
        return new NuubitWebViewClient();
    }

    public static NuubitWebChromeClient createWebChromeClient() {
        return new NuubitWebChromeClient();
    }

    public static OkHttpClient OkHttpCreate() {
        return OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false);
    }
    public static OkHttpClient OkHttpCreate(int timeoutSec) {
        return OkHttpCreate(timeoutSec, false, false);
    }

    public static OkHttpClient OkHttpCreate(int timeoutSec, boolean followRedirect, boolean followSllRedirect) {
        if(client != null) return client;

        //ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient
                .addInterceptor(new NuubitInterceptor())
                .connectTimeout(timeoutSec, TimeUnit.SECONDS)
                //.sslSocketFactory(NuubitSecurity.getSSLSocketFactory(), NuubitSecurity.getTrustManager())
                .followRedirects(followRedirect)
                .followSslRedirects(followSllRedirect).cookieJar(new NuubitCookie());
        client  = httpClient.build();

        List<Interceptor> inter = client.interceptors();
        for(Interceptor in : inter){
            Log.i("INTERCEPTOR", in.toString());
        }
        return client;
    }

    public static OkHttpClient getClient(){return client;}

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
                .registerTypeAdapter(LogEvent.class, new EventSerialize())
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
            result = tag.getName().equals(NuubitConstants.SYSTEM_REQUEST) && ((boolean) tag.getValue());
        } catch (Exception ex) {
            result = false;
        }
        return result;
        //return false;
    }

    public static boolean isFree(Request req) {
        boolean result = false;
        try {
            Tag tag = (Tag) req.tag();
            result = tag.getName().equals(NuubitConstants.FREE_REQUEST) && ((boolean) tag.getValue());
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private static boolean isStat(Request req) {
        String statURL = NuubitApplication.getInstance().getConfig().getParam().get(0).getStatsReportingUrl();
        return req.url().toString().equals(statURL);
    }

    public static Request processingRequest(Request original, boolean bad) {
        StringBuilder sHeaders = new StringBuilder();
        for(String name : original.headers().names()){
            sHeaders.append("\n"+name);
            sHeaders.append(":");
            sHeaders.append(original.headers().get(name)+"\n");
        }
        Log.i(TAG, "Original:" + original.toString()+"\n Headers: "+sHeaders);

        RequestCreator creator = new RequestCreator(NuubitApplication.getInstance().getConfig());
        Request result = creator.create(original);

        if (bad) {
            HttpUrl badURL = result.url();
            String sHost = badURL.host();
            String sHostNew = sHost.substring(0, sHost.length() - 4);
            badURL = badURL.newBuilder().host(sHost).build();
            result = result.newBuilder().url(badURL).build();
        }


        sHeaders = new StringBuilder();
        for(String name : result.headers().names()){
            sHeaders.append("\n"+name);
            sHeaders.append(":");
            sHeaders.append(result.headers().get(name)+"\n");
        }

        Log.i(TAG, "Transfered: \n" + result.toString()+"\n Headers: "+sHeaders.toString());
        return result; //creator.create(original);
    }


    public static String getEncode(Request req) {
        String result = "ISO-8859-1";
        String header = req.header("Content-Type");
        if (header == null || header.isEmpty()) return result;
        String[] sp = header.split(";");
        if (sp.length > 1 && !sp[1].isEmpty()) result = sp[1];
        return result;
    }

    public static String getContentType(Request req) {
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
            OperationMode mode = NuubitApplication.getInstance().getConfig().getParam().get(0).getOperationMode();
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
