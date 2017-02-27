package com.rev.revsdk.statistic.sections;

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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.rev.revsdk.RevApplication;
import com.rev.revsdk.database.RequestTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Requests extends ArrayList<RequestOne> {
    private static final String TAG = Requests.class.getSimpleName();
    private Context context;
    public Requests(Context context){
        this.context = context;
        readRequests();
    }
    private void readRequests(){
        this.clear();
        String[] args = {"1", "0"};
        ContentValues update = new ContentValues();
        update.put(RequestTable.Columns.SENT, 1);
        context.getContentResolver().update(RequestTable.URI, update, null, null);
        Cursor c = context.getContentResolver().query(RequestTable.URI, null, RequestTable.Columns.SENT + "=? AND " + RequestTable.Columns.CONFIRMED + "=?", args, null);
        List<RequestOne> rows = RequestTable.listFromCursor(c);
        int perReport = RevApplication.getInstance().getConfig().getParam().get(0).getStatsReportingMaxRequestsPerReport();
        Log.i(TAG, "Max per report:" + String.valueOf(perReport));
        List<RequestOne> sub = rows.subList(0, perReport > rows.size() ? rows.size() : perReport);
        addAll(sub);
        Log.i(TAG, "Size : "+String.valueOf(size()));
        Collections.sort(this, new Comparator<RequestOne>() {
            @Override
            public int compare(RequestOne o1, RequestOne o2) {
                if (o1.getID() > o2.getID()) return 1;
                else if (o1.getID() == o2.getID()) return 0;
                else return -1;
            }
        });
        int i = 0;
    }
}
