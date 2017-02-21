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

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rev.revsdk.database.RequestTable;

import java.util.ArrayList;

public class Requests extends ArrayList<RequestOne> {
    private static final String TAG = Requests.class.getSimpleName();
    private Context context;
    public Requests(Context context){
        this.context = context;
        readRequests();
    }
    private void readRequests(){
        String[] args = {"0","0"};
        Cursor c = context.getContentResolver().query(RequestTable.URI,null, RequestTable.Columns.SENDED+"=? OR "+RequestTable.Columns.CONFIRMED+"=?", args, null);
        addAll(RequestTable.listFromCursor(c));
        Log.i(TAG, "Size : "+String.valueOf(size()));
    }
}
