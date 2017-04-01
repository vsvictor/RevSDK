package com.rev.weather;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rev.sdk.RevApplication;
import com.rev.sdk.RevSDK;
import com.rev.weather.model.Root;
import com.rev.weather.volley.OkHttp3Stack;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

public class RevApp extends RevApplication {

    private static RevApp instance;

    private Retrofit retrofit;
    private Root root;
    private OkHttpClient client;
    private Gson gson;
    private static IWeather api;
    private static String key;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // For Retrofit
        client = RevSDK.OkHttpCreate(Const.TIME_OUT, false, false);
        gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        api = retrofit.create(IWeather.class);

        key = getResources().getString(R.string.key);

    }

    public static RevApp getInstance() {
        return instance;
    }

    public static IWeather getAPI() {
        return api;
    }

    public static String getKey() {
        return key;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }


    public OkHttpClient getHTTPClient() {
        return client;
    }

    @NonNull
    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttp3Stack());
        }

        return mRequestQueue;
    }

    private static void addRequest(@NonNull final Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(@NonNull final Request<?> request, @NonNull final String tag) {
        request.setTag(tag);
        addRequest(request);
    }
}