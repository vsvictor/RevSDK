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
import com.rev.sdk.database.AppTable;
import com.rev.sdk.statistic.sections.App;

import java.lang.reflect.Type;

public class AppDeserialize implements JsonDeserializer<App> {
    @Override
    public App deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        App result = new App();
        JsonObject obj = (JsonObject) json;
        result.setVersion(obj.get(AppTable.Columns.VERSION).getAsString());
        result.setAppName(obj.get(AppTable.Columns.APP_NAME).getAsString());
        result.setSDKKey(obj.get(AppTable.Columns.SDK_KEY).getAsString());
        result.setSDKVersion(obj.get(AppTable.Columns.SDK_VERSION).getAsString());
        return result;
    }
}
