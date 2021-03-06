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
    public void onHandleIntent(Intent intent) {
        int timeOut = NuubitConstants.DEFAULT_TIMEOUT_SEC;
        String url = null;

        Log.i(TAG, "Statist running 0...");

        long lastTimeSuccess = 0;
        Log.i(TAG, "Statist running 1...");
        long lastTimeFail = 0;
        Log.i(TAG, "Statist running 2...");
        HTTPCode resCode = HTTPCode.UNDEFINED;
        //int resCode = -1;
        Log.i(TAG, "Statist running 3...");
        long count = 0;
        Log.i(TAG, "Statist running 4...");
        String reason = NuubitConstants.UNDEFINED;
        Log.i(TAG, "Statist running 5...");

        //statistic = new Statistic(getApplicationContext());
        statistic = new Statistic(NuubitApplication.getInstance());

/*
        Log.i(TAG, "Load statistic...");
        String st = intent.getExtras().getString("stat");
        String stat;
        if(st != null) {
            Log.i(TAG, "Begin...");
            statistic = NuubitSDK.gsonCreate().fromJson(st, Statistic.class);
            Log.i(TAG,"End");
            stat = st;
            Log.i(TAG, "Stat loaded");
        } else{
            Log.i(TAG, "Create statistic...");
            statistic = new Statistic(getApplicationContext());
            stat = NuubitSDK.gsonCreate().toJson(statistic);
        }
*/
        Log.i(TAG, "Statistic created");

        if(statistic.getRequests().size()>0) {

            String stat = NuubitSDK.gsonCreate().toJson(statistic);
            Log.i(TAG + " stat", "\n\n" + statistic.toString());

            if (intent != null) {
                Bundle params = intent.getExtras();
                if (params != null) {
                    timeOut = params.getInt(NuubitConstants.TIMEOUT, NuubitConstants.DEFAULT_TIMEOUT_SEC);
                    url = params.getString(NuubitConstants.STATISTIC);
                }
            }
            OkHttpClient client = NuubitSDK.OkHttpCreate(timeOut);

            Request req = new Request.Builder()
                    .url(url)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(RequestBody.create(MediaType.parse("application/json"), stat))
                    .tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true))
                    .build();
            Response response;
            try {
                response = client.newCall(req).execute();
                resCode = HTTPCode.create(response.code());
                //resCode = response.code();
                Log.i(TAG, "Response code: "+resCode);
                if (resCode.getType() == HTTPCode.Type.SUCCESSFULL) {
                //if (resCode<400) {
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
                //resCode = HTTPCode.BAD_REQUEST;
                lastTimeFail = System.currentTimeMillis();
                reason = "Null response";
                ex.printStackTrace();
            } catch (IOException ex) {
                //resCode = HTTPCode.BAD_REQUEST;
                lastTimeFail = System.currentTimeMillis();
                reason = "I/O exception";
                ex.printStackTrace();
            }
       }
        reason = NuubitConstants.STAT_NO_REQUEST;
        //resCode = HTTPCode.OK;
        //resCode = 200;
        Intent statIntent = new Intent(NuubitActions.STAT_ACTION);
        statIntent.putExtra(NuubitConstants.HTTP_RESULT, resCode.getCode());
        //statIntent.putExtra(NuubitConstants.HTTP_RESULT, resCode);
        statIntent.putExtra(NuubitConstants.STAT_REQUESTS_COUNT, count);
        statIntent.putExtra(NuubitConstants.STATISTIC, String.valueOf(statistic.getRequests().size()+" requests sent"));
        statIntent.putExtra(NuubitConstants.STAT_LAST_TIME_SUCCESS, lastTimeSuccess);
        statIntent.putExtra(NuubitConstants.STAT_LAST_TIME_FAIL, lastTimeFail);
        statIntent.putExtra(NuubitConstants.STAT_LAST_FAIL_REASON, reason);
        NuubitApplication.getInstance().sendBroadcast(statIntent);
        Log.i(TAG, "SendBroadcast");
    }
}
