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
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rev.revsdk.Actions;
import com.rev.revsdk.Constants;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.database.RequestTable;
import com.rev.revsdk.statistic.Statistic;
import com.rev.revsdk.statistic.sections.RequestOne;
import com.rev.revsdk.utils.Tag;

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
        int timeOut = Constants.DEFAULT_TIMEOUT_SEC;
        String url = null;

        Log.i(TAG, "Statist running...");
        //String rssi = intent.getExtras().getString(Constants.RSSI, "-10000");
        //String rssiAverage = intent.getExtras().getString(Constants.RSSI_AVERAGE, "-10000");
        //String rssiBest = intent.getExtras().getString(Constants.RSSI_BEST, "-10000");

        statistic = new Statistic(getApplicationContext()); //, rssi, rssiAverage, rssiBest);
        String stat = RevSDK.gsonCreate().toJson(statistic);
        Log.i(TAG+" stat", "\n\n"+stat);

        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                timeOut = params.getInt(Constants.TIMEOUT, Constants.DEFAULT_TIMEOUT_SEC);
                url = params.getString(Constants.STATISTIC);
            }
        }
        OkHttpClient client = RevSDK.OkHttpCreate(timeOut);

        Request req = new Request.Builder()
                .url(url)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(RequestBody.create(MediaType.parse("application/json"),stat))
                .tag(new Tag(Constants.SYSTEM_REQUEST, true))
                .build();
        Response response = null;

        try {
            response = client.newCall(req).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response.code() == 200){
            ArrayList<RequestOne> rows = statistic.getRequests();
            ContentValues values = new ContentValues();
            values.put(RequestTable.Columns.CONFIRMED, 1);
            int count = 0;
            if(statistic.getRequests().size()>0) {
                long mixIndex = statistic.getRequests().get(0).getID();
                long maxIndex = statistic.getRequests().get(statistic.getRequests().size() - 1).getID();
                count = getApplicationContext().getContentResolver().update(RequestTable.URI,
                        values,
                        RequestTable.Columns.ID + ">=? AND " + RequestTable.Columns.ID + "<=?",
                        new String[]{String.valueOf(mixIndex), String.valueOf(maxIndex)});
            }
            Log.i(TAG, "Updated: "+String.valueOf(count));
        }

        Intent statIntent = new Intent(Actions.STAT_ACTION);
        statIntent.putExtra(Constants.STATISTIC, response.toString());
        sendBroadcast(statIntent);
        Log.i(TAG, response.toString());
        Log.i(TAG, "Statistic saved!!!");
    }
}
