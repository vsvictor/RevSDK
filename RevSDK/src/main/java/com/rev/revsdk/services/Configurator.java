package com.rev.revsdk.services;

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

import com.rev.revsdk.Actions;
import com.rev.revsdk.Constants;
import com.rev.revsdk.RevApplication;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.types.HTTPCode;
import com.rev.revsdk.types.Tag;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Configurator extends IntentService {

    private static final String TAG = Configurator.class.getSimpleName();

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
        int timeOut = Constants.DEFAULT_TIMEOUT_SEC;
        String url = Constants.MAIN_CONFIG_URL;
        String key =((RevApplication)getApplicationContext()).getSDKKey();

        String result = null;

        Log.i(TAG, "Running...");
        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                timeOut = params.getInt(Constants.TIMEOUT, Constants.DEFAULT_TIMEOUT_SEC);
                url = params.getString(Constants.CONFIG, Constants.MAIN_CONFIG_URL);
            }
        }
        OkHttpClient client = RevSDK.OkHttpCreate(timeOut);
        Log.i(TAG, url+"/"+key);
        Request req = new Request.Builder()
                .url(url+"/"+key)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .tag(new Tag(Constants.SYSTEM_REQUEST, true))
                .build();
        Response response = null;
        try {
            response = client.newCall(req).execute();
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        } catch (NullPointerException ex) {
            response = null;
            ex.printStackTrace();
        }
        try {
            if (response == null) throw new IOException("Response null");
            HTTPCode resCode = HTTPCode.create(response.code());
            if (resCode.getType() == HTTPCode.Type.SUCCESSFULL) {
                result = response.body().string();
            } else {
                result = resCode.getMessage();
                Log.i(TAG, "Request error!!! Status code:" + String.valueOf(response.code()));
            }
            Intent configIntent = new Intent(Actions.CONFIG_UPDATE_ACTION);
            configIntent.putExtra(Constants.HTTP_RESULT, resCode.getCode());
            configIntent.putExtra(Constants.CONFIG, result);
            sendBroadcast(configIntent);
        } catch (IOException ex) {
            ex.printStackTrace();
            Intent configIntent = new Intent(Actions.CONFIG_UPDATE_ACTION);
            sendBroadcast(configIntent);
        }
    }
}
