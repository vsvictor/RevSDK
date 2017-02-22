package com.rev.revsdk.statistic.serialize;

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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.database.RequestTable;
import com.rev.revsdk.statistic.sections.RequestOne;

import java.lang.reflect.Type;

public class RequestOneSerialize implements JsonSerializer<RequestOne>{

    @Override
    public JsonElement serialize(RequestOne src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty(RequestTable.Columns.CONNECTION_ID, src.getConnectionID());
        result.addProperty(RequestTable.Columns.CONTENT_ENCODING, src.getContentEncode());
        result.addProperty(RequestTable.Columns.CONTENT_TYPE, src.getContentType());
        result.addProperty(RequestTable.Columns.START_TS, src.getStartTS());
        result.addProperty(RequestTable.Columns.END_TS, src.getEndTS());
        result.addProperty(RequestTable.Columns.FIRST_BYTE_TIMESTAMP, src.getFirstByteTime());
        result.addProperty(RequestTable.Columns.KEEP_ALIVE_STATUS, src.getKeepAliveStatus());
        result.addProperty(RequestTable.Columns.LOCAL_CACHE_STATUS, src.getLocalCacheStatus());
        result.addProperty(RequestTable.Columns.METHOD, src.getMethod());
        result.addProperty(RequestTable.Columns.NETWORK, src.getNetwork());
        result.addProperty(RequestTable.Columns.PROTOCOL, src.getProtocol().toString());
        result.addProperty(RequestTable.Columns.RECEIVED_BYTES, src.getReceivedBytes());
        result.addProperty(RequestTable.Columns.SENT_BYTES, src.getSentBytes());
        result.addProperty(RequestTable.Columns.STATUS_CODE, src.getStatusCode());
        result.addProperty(RequestTable.Columns.SUCCESS_CODE, src.getSuccessStatus());
        result.addProperty(RequestTable.Columns.TRANSPORT_PROTOCOL, src.getTransportProtocol().toString());
        result.addProperty(RequestTable.Columns.URL, src.getURL());

        return result;
    }
}