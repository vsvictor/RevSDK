package com.rev.weather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rev.sdk.RevApplication;
import com.rev.sdk.RevSDK;

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
    private Retrofit retrofit;
    private OkHttpClient client;
    private Gson gson;
    private static IWeather api;
    private static String key;

    @Override
    public void onCreate() {
        super.onCreate();
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

    public static IWeather getAPI() {
        return api;
    }

    public static String getKey() {
        return key;
    }
}