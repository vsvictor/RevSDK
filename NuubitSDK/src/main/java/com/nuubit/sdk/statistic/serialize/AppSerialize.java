package com.nuubit.sdk.statistic.serialize;

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
import com.nuubit.sdk.database.AppTable;
import com.nuubit.sdk.statistic.sections.App;

import java.lang.reflect.Type;

public class AppSerialize implements JsonSerializer<App> {
    @Override
    public JsonElement serialize(App src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty(AppTable.Columns.VERSION, src.getVersion());
        result.addProperty(AppTable.Columns.APP_NAME, src.getAppName());
        result.addProperty(AppTable.Columns.SDK_KEY, src.getSDKKey());
        result.addProperty(AppTable.Columns.SDK_VERSION, src.getSDKVersion());
        return result;
    }
}
