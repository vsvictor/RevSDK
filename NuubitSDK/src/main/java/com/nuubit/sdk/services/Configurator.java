package com.nuubit.sdk.services;

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

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.config.Config;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Configurator extends IntentService {

    private static final String TAG = Configurator.class.getSimpleName();

    private boolean isNow = false;

    public Configurator() {
        this("Configurator");
    }

    public Configurator(String name) {
        super(name);
    }

    @Override
    public void onCreate(){
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int timeOut = NuubitConstants.DEFAULT_TIMEOUT_SEC;
        String url = NuubitConstants.DEFAULT_CONFIG_URL;
        String key = ((NuubitApplication) getApplication()).getSDKKey();

        String result = null;

        Log.i(TAG, "Running...");
        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                timeOut = params.getInt(NuubitConstants.TIMEOUT, NuubitConstants.DEFAULT_TIMEOUT_SEC);
                url = params.getString(NuubitConstants.CONFIG, NuubitConstants.DEFAULT_CONFIG_URL);
                isNow = params.getBoolean(NuubitConstants.NOW);
            }
        }
        OkHttpClient client = NuubitSDK.OkHttpCreate(timeOut);
        Log.i(TAG, url+"/"+key);
        Request req = new Request.Builder()
                .url(url+"/"+key)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true))
                .build();
        Response response = null;
/*
        try {

        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        } catch (NullPointerException ex) {
            response = null;
            ex.printStackTrace();
        }
*/
        try {
            response = client.newCall(req).execute();
            if (response == null) throw new IOException("Response null");
            HTTPCode resCode = HTTPCode.create(response.code());
            if (resCode.getType() == HTTPCode.Type.SUCCESSFULL) {
                result = response.body().string();
            } else {
                result = resCode.getMessage();
                Log.i(TAG, "Request error!!! Status code:" + String.valueOf(response.code())+" :"+response.message());
            }
            Intent configIntent = new Intent(NuubitActions.CONFIG_UPDATE_ACTION);
            configIntent.putExtra(NuubitConstants.HTTP_RESULT, resCode.getCode());
            configIntent.putExtra(NuubitConstants.CONFIG, result);
            Log.i(TAG, result);
            sendBroadcast(configIntent);
            Log.i(TAG, result);
        } catch (Exception ex) {
            //ex.printStackTrace();
            Log.i(TAG, "((((((((((((((((((((((((((((( Error )))))))))))))))))))))))))");
            Intent configIntent = new Intent(NuubitActions.CONFIG_UPDATE_ACTION);
            if (isNow) {
                configIntent.putExtra(NuubitConstants.HTTP_RESULT, 200);
                Config def = new Config();
                String defJSON = NuubitSDK.gsonCreate().toJson(def, Config.class);
                Log.i(TAG, defJSON);
                configIntent.putExtra(NuubitConstants.CONFIG, defJSON);
            } else {
                configIntent.putExtra(NuubitConstants.HTTP_RESULT, 400);
            }
            sendBroadcast(configIntent);
            ex.printStackTrace();
        }
    }
}
