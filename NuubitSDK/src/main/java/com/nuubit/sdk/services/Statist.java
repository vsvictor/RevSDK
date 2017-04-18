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
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.Statistic;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Statist extends IntentService {
    private static final String TAG = Statist.class.getSimpleName();
    private Statistic statistic;
    public Statist(){
        this("Statist");
    }
    public Statist(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int timeOut = NuubitConstants.DEFAULT_TIMEOUT_SEC;
        String url = null;

        Log.i(TAG, "Statist running...");

        statistic = new Statistic(getApplicationContext());
        String stat = NuubitSDK.gsonCreate().toJson(statistic);
        Log.i(TAG+" stat", "\n\n"+stat);

        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                timeOut = params.getInt(NuubitConstants.TIMEOUT, NuubitConstants.DEFAULT_TIMEOUT_SEC);
                url = params.getString(NuubitConstants.STATISTIC);
            }
        }
        OkHttpClient client = NuubitSDK.OkHttpCreate(timeOut);

        Request req = new Request.Builder()
                .url(url)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(RequestBody.create(MediaType.parse("application/json"),stat))
                //.tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true))
                .build();
        Response response;
        long lastTimeSuccess = 0;
        long lastTimeFail = 0;
        HTTPCode resCode;
        long count = 0;
        String reason = "";
        try {
            response = client.newCall(req).execute();
            resCode = HTTPCode.create(response.code());
            if (resCode.getType() == HTTPCode.Type.SUCCESSFULL) {
                ArrayList<RequestOne> rows = statistic.getRequests();
                ContentValues values = new ContentValues();
                values.put(RequestTable.Columns.SENT, 1);
                values.put(RequestTable.Columns.CONFIRMED, 1);
                count = 0;
                if (!statistic.getRequests().isEmpty()) {
                    long mixIndex = statistic.getRequests().get(0).getID();
                    long maxIndex = statistic.getRequests().get(statistic.getRequests().size() - 1).getID();
                    count = NuubitApplication.getInstance().getDatabase().updateRequestFromTo(values, mixIndex, maxIndex);
                }
                Log.i(TAG, "Updated: " + String.valueOf(count));
                lastTimeSuccess = System.currentTimeMillis();
            }
        } catch (NullPointerException ex) {
            resCode = HTTPCode.BAD_REQUEST;
            lastTimeFail = System.currentTimeMillis();
            reason = "Null response";
            ex.printStackTrace();
        } catch (IOException ex) {
            resCode = HTTPCode.BAD_REQUEST;
            lastTimeFail = System.currentTimeMillis();
            reason = "I/O exception";
            ex.printStackTrace();
        }

        Intent statIntent = new Intent(NuubitActions.STAT_ACTION);
        statIntent.putExtra(NuubitConstants.HTTP_RESULT, resCode.getCode());
        statIntent.putExtra(NuubitConstants.STAT_REQUESTS_COUNT, count);
        statIntent.putExtra(NuubitConstants.STATISTIC, "Test");
        statIntent.putExtra(NuubitConstants.STAT_LAST_TIME_SUCCESS, lastTimeSuccess);
        statIntent.putExtra(NuubitConstants.STAT_LAST_TIME_FAIL, lastTimeFail);
        statIntent.putExtra(NuubitConstants.STAT_LAST_FAIL_REASON, reason);
        sendBroadcast(statIntent);
    }
}
