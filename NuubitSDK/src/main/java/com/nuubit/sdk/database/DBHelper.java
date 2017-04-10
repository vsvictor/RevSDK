package com.nuubit.sdk.database;

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
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DBHelper extends SQLiteOpenHelper{
    private static final String TAG = DBHelper.class.getSimpleName();
    public static Uri BASE_CONTENT_URI;
    public static String DATABASE_NAME = "database.db";
    public static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String baseURI) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        BASE_CONTENT_URI = Uri.parse("content://" + baseURI);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AppTable.Requests.CREATE_REQUEST);
        db.execSQL(RequestTable.Requests.CREATE_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RequestTable.Requests.DROP_REQUEST);
        db.execSQL(AppTable.Requests.DROP_REQUEST);
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RequestTable.Requests.TABLE_NAME, null, null);
    }

    public long insertRequest(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(RequestTable.Requests.TABLE_NAME, null, values);
        return id;
    }

    public int updateRequest(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(RequestTable.Requests.TABLE_NAME, values, null, null);
    }

    public int updateRequestFromTo(ContentValues values, long from, long to) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(RequestTable.Requests.TABLE_NAME,
                values,
                RequestTable.Columns.ID + ">=? AND " + RequestTable.Columns.ID + "<=?",
                new String[]{String.valueOf(from), String.valueOf(to)});
    }

    public Cursor getUnsent() {
        String[] args = {"1", "0"};
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RequestTable.Requests.TABLE_NAME, null, RequestTable.Columns.SENT + "=? AND " + RequestTable.Columns.CONFIRMED + "=?", args, null, null, null);
    }
}
