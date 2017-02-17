package com.rev.revsdk.database;

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
import android.net.Uri;
import android.support.annotation.NonNull;

import com.rev.revsdk.database.data.App;

import java.util.ArrayList;
import java.util.List;

public class AppTable {
    private static final String TAG = "AppTable";
    public static final Uri URI = DBHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void insert(Context context, App app){
        context.getContentResolver().insert(URI, toContentValues(app));
    }
    public static void insert(Context context, @NonNull List<App> apps){
        ContentValues[] values = new ContentValues[apps.size()];
        for(int i = 0; i<apps.size();i++){
            values[i] = toContentValues(apps.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }
    @NonNull
    public static App fromCursor(@NonNull Cursor cursor){
        App value = new App();
        value.setID(cursor.getLong(cursor.getColumnIndex(Columns.ID)));
        value.setAppName(cursor.getString(cursor.getColumnIndex(Columns.APP_NAME)));
        value.setVersion(cursor.getString(cursor.getColumnIndex(Columns.VERSION)));
        value.setSDKKey(cursor.getString(cursor.getColumnIndex(Columns.SDK_KEY)));
        value.setSDKVersion(cursor.getString(cursor.getColumnIndex(Columns.SDK_VERSION)));
        return value;
    }
    @NonNull
    public static List<App> listFromCursor(@NonNull Cursor cursor){
        List<App> values = new ArrayList<App>();
        if(!cursor.moveToFirst()){
            return values;
        }
        try {
            do {
                values.add(fromCursor(cursor));
            }while (cursor.moveToNext());
        }finally {
            cursor.close();
        }
        return values;
    }
    @NonNull
    public static void clear(Context context){
        context.getContentResolver().delete(URI, null, null);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull App app) {
        ContentValues values = new ContentValues();
        values.put(Columns.VERSION, app.getVersion());
        values.put(Columns.APP_NAME, app.getAppName());
        values.put(Columns.SDK_KEY, app.getSDKKey());
        values.put(Columns.SDK_VERSION, app.getSDKVersion());
        return values;
    }
    public interface Columns{
        String ID = "id";
        String VERSION = "version";
        String APP_NAME = "app_name";
        String SDK_KEY = "sdk_key";
        String SDK_VERSION = "sdk_version";
    }
    public interface Requests{
        String TABLE_NAME = AppTable.class.getSimpleName();
        String CREATE_REQUEST = "CREATE TABLE "+TABLE_NAME+" ("+
                Columns.ID+" int not null autoincrement,"+
                Columns.VERSION+" varchar(64) not null,"+
                Columns.APP_NAME+" varchar(128) not null,"+
                Columns.SDK_KEY+" varchar(64) not null,"+
                Columns.SDK_VERSION+" varchar(64) not null);";
        String DROP_REQUEST = "DROP TABLE IF EXIST "+TABLE_NAME;
    }
}
