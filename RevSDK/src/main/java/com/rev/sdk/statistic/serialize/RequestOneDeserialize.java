package com.rev.sdk.statistic.serialize;

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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rev.sdk.database.RequestTable;
import com.rev.sdk.protocols.Protocol;
import com.rev.sdk.statistic.sections.RequestOne;

import java.lang.reflect.Type;

public class RequestOneDeserialize implements JsonDeserializer<RequestOne> {
    @Override
    public RequestOne deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RequestOne result = new RequestOne();
        JsonObject obj = (JsonObject) json;
        result.setConnectionID(obj.get(RequestTable.Columns.CONNECTION_ID).getAsInt());
        result.setContentEncode(obj.get(RequestTable.Columns.CONTENT_ENCODING).getAsString());
        result.setContentType(obj.get(RequestTable.Columns.CONTENT_TYPE).getAsString());
        result.setStartTS(obj.get(RequestTable.Columns.START_TS).getAsLong());
        result.setEndTS(obj.get(RequestTable.Columns.END_TS).getAsLong());
        result.setFirstByteTime(obj.get(RequestTable.Columns.FIRST_BYTE_TIMESTAMP).getAsLong());
        result.setKeepAliveStatus(obj.get(RequestTable.Columns.KEEP_ALIVE_STATUS).getAsInt());
        result.setLocalCacheStatus(obj.get(RequestTable.Columns.LOCAL_CACHE_STATUS).getAsString());
        result.setMethod(obj.get(RequestTable.Columns.METHOD).getAsString());
        result.setNetwork(obj.get(RequestTable.Columns.NETWORK).getAsString());
        result.setProtocol(Protocol.fromString(obj.get(RequestTable.Columns.PROTOCOL).getAsString()));
        result.setReceivedBytes(obj.get(RequestTable.Columns.RECEIVED_BYTES).getAsLong());
        result.setSentBytes(obj.get(RequestTable.Columns.SENT_BYTES).getAsLong());
        result.setStatusCode(obj.get(RequestTable.Columns.STATUS_CODE).getAsInt());
        result.setSuccessStatus(obj.get(RequestTable.Columns.SUCCESS_CODE).getAsInt());
        result.setTransportProtocol(Protocol.fromString(obj.get(RequestTable.Columns.TRANSPORT_PROTOCOL).getAsString()));
        result.setURL(obj.get(RequestTable.Columns.URL).getAsString());
        return result;
    }
}
