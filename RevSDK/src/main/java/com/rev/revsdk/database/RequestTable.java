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

import com.rev.revsdk.RevApplication;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.statistic.sections.RequestOne;

import java.util.ArrayList;
import java.util.List;

public class RequestTable {
    private static final String TAG = "RequestTable";
    public static final Uri URI = DBHelper.BASE_CONTENT_URI.buildUpon().appendPath(RequestTable.Requests.TABLE_NAME).build();

    public static Uri insert(Context context, RequestOne req){
        RevApplication con = (RevApplication) context;
        return context.getContentResolver().insert(URI, toContentValues(con.getConfig().getAppName(), req));
    }
    public static void insert(Context context, @NonNull List<RequestOne> reqs){
        RevApplication con = (RevApplication) context;
        ContentValues[] values = new ContentValues[reqs.size()];
        for(int i = 0; i<reqs.size();i++){
            values[i] = toContentValues(con.getConfig().getAppName(), reqs.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }
    @NonNull
    public static RequestOne fromCursor(@NonNull Cursor cursor){
        RequestOne value = new RequestOne();
        value.setID(cursor.getLong(cursor.getColumnIndex(Columns.ID)));
        value.setConnectionID(cursor.getInt(cursor.getColumnIndex(Columns.CONNECTION_ID)));
        value.setContentEncode(cursor.getString(cursor.getColumnIndex(Columns.CONTENT_ENCODING)));
        value.setContentType(cursor.getString(cursor.getColumnIndex(Columns.CONTENT_TYPE)));
        value.setStartTS(cursor.getLong(cursor.getColumnIndex(Columns.START_TS)));
        value.setEndTS(cursor.getLong(cursor.getColumnIndex(Columns.END_TS)));
        value.setFirstByteTime(cursor.getLong(cursor.getColumnIndex(Columns.FIRST_BYTE_TIMESTAMP)));
        value.setKeepAliveStatus(cursor.getInt(cursor.getColumnIndex(Columns.KEEP_ALIVE_STATUS)));
        value.setLocalCacheStatus(cursor.getString(cursor.getColumnIndex(Columns.LOCAL_CACHE_STATUS)));
        value.setMethod(cursor.getString(cursor.getColumnIndex(Columns.METHOD)));
        value.setNetwork(cursor.getString(cursor.getColumnIndex(Columns.NETWORK)));
        value.setProtocol(Protocol.fromString(cursor.getString(cursor.getColumnIndex(Columns.PROTOCOL))));
        value.setReceivedBytes(cursor.getLong(cursor.getColumnIndex(Columns.RECEIVED_BYTES)));
        value.setSentBytes(cursor.getLong(cursor.getColumnIndex(Columns.SENT_BYTES)));
        value.setStatusCode(cursor.getInt(cursor.getColumnIndex(Columns.STATUS_CODE)));
        value.setSuccessStatus(cursor.getInt(cursor.getColumnIndex(Columns.SUCCESS_CODE)));
        value.setTransportProtocol(Protocol.fromString(cursor.getString(cursor.getColumnIndex(Columns.TRANSPORT_PROTOCOL))));
        value.setURL(cursor.getString(cursor.getColumnIndex(Columns.URL)));
        value.setDestination(cursor.getString(cursor.getColumnIndex(Columns.DESTINATION)));
        value.setXRevCach(cursor.getString(cursor.getColumnIndex(Columns.X_REV_CACHE)));
        value.setDomain(cursor.getString(cursor.getColumnIndex(Columns.DOMAIN)));
        value.setEdgeTransport(Protocol.fromString(cursor.getString(cursor.getColumnIndex(Columns.EDGE_TRANSPORT))));
        return value;
    }

    @NonNull
    public static List<RequestOne> listFromCursor(@NonNull Cursor cursor){
        List<RequestOne> values = new ArrayList<RequestOne>();
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
    public static ContentValues toContentValues(String appName, @NonNull RequestOne req) {
        ContentValues values = new ContentValues();

        values.put(Columns.APP_NAME, appName);
        values.put(Columns.CONNECTION_ID, req.getConnectionID());
        values.put(Columns.CONTENT_ENCODING, req.getContentEncode());
        values.put(Columns.CONTENT_TYPE, req.getContentType());
        values.put(Columns.START_TS, req.getStartTS());
        values.put(Columns.END_TS, req.getEndTS());
        values.put(Columns.FIRST_BYTE_TIMESTAMP, req.getFirstByteTime());
        values.put(Columns.KEEP_ALIVE_STATUS, req.getKeepAliveStatus());
        values.put(Columns.LOCAL_CACHE_STATUS, req.getLocalCacheStatus());
        values.put(Columns.METHOD, req.getMethod());
        values.put(Columns.NETWORK, req.getNetwork());
        values.put(Columns.PROTOCOL, req.getProtocol().toString());
        values.put(Columns.RECEIVED_BYTES, req.getReceivedBytes());
        values.put(Columns.SENT_BYTES, req.getSentBytes());
        values.put(Columns.STATUS_CODE, req.getStatusCode());
        values.put(Columns.SUCCESS_CODE, req.getSuccessStatus());
        values.put(Columns.TRANSPORT_PROTOCOL, req.getTransportProtocol().toString());
        values.put(Columns.URL, req.getURL());
        values.put(Columns.DESTINATION, req.getDestination());
        values.put(Columns.X_REV_CACHE, req.getXRevCach());
        values.put(Columns.DOMAIN, req.getDomain());
        values.put(Columns.EDGE_TRANSPORT, req.getEdgeTransport().toString());
        return values;
    }
    public interface Columns{
        final String ID = "id";
        final String APP_NAME = "app_id";
        final String CONNECTION_ID = "conn_id";
        final String CONTENT_ENCODING = "cont_encoding";
        final String CONTENT_TYPE = "cont_type";
        final String START_TS = "start_ts";
        final String END_TS = "end_ts";
        final String FIRST_BYTE_TIMESTAMP = "first_byte_ts";
        final String KEEP_ALIVE_STATUS = "keepalive_status";
        final String LOCAL_CACHE_STATUS = "local_cache_status";
        final String METHOD = "method";
        final String NETWORK = "network";
        final String PROTOCOL = "protocol";
        final String RECEIVED_BYTES = "received_bytes";
        final String SENT_BYTES = "sent_bytes";
        final String STATUS_CODE = "status_code";
        final String SUCCESS_CODE = "success_status";
        final String TRANSPORT_PROTOCOL = "transport_protocol";
        final String URL = "url";
        final String DESTINATION = "destination";
        final String X_REV_CACHE = "x_rev_cache";
        final String DOMAIN = "domain";
        final String SENDED = "sended";
        final String CONFIRMED = "confirmed";
        final String EDGE_TRANSPORT = "edge_transport";
    }
    public interface Requests{
        final String TABLE_NAME = RequestTable.class.getSimpleName();
        final String CREATE_REQUEST = "CREATE TABLE "+TABLE_NAME+" (" +
                Columns.ID + " integer primary key autoincrement," +
                Columns.APP_NAME + " varchar(128) not null," +
                Columns.CONNECTION_ID + " int not null," +
                Columns.CONTENT_ENCODING + " varchar(128) not null," +
                Columns.CONTENT_TYPE + " varchar(64) not null," +
                Columns.START_TS + " int not null," +
                Columns.END_TS + " int not null," +
                Columns.FIRST_BYTE_TIMESTAMP + " int not null," +
                Columns.KEEP_ALIVE_STATUS + " int not null," +
                Columns.LOCAL_CACHE_STATUS + " varchar(64) not null," +
                Columns.METHOD + " varchar(8) not null," +
                Columns.NETWORK + " varchar(64) not null," +
                Columns.PROTOCOL + " varchar(8) not null," +
                Columns.RECEIVED_BYTES + " int not null," +
                Columns.SENT_BYTES + " int not null," +
                Columns.STATUS_CODE + " int not null," +
                Columns.SUCCESS_CODE + " int not null," +
                Columns.TRANSPORT_PROTOCOL + " varchar(8) not null," +
                Columns.URL + " varchar(256) not null," +
                Columns.DESTINATION + " varchar(256) not null," +
                Columns.X_REV_CACHE + " varchar(16) not null," +
                Columns.EDGE_TRANSPORT + " varchar(16) not null," +
                Columns.DOMAIN + " varchar(256) not null," +
                Columns.SENDED + " int(1) not null default 0," +
                Columns.CONFIRMED + " int(1) not null default 0);";

        final String DROP_REQUEST = "DROP TABLE IF EXIST " + TABLE_NAME;
    }
}
