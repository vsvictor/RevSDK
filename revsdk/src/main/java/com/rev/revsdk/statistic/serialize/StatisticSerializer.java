package com.rev.revsdk.statistic.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.Phone;
import com.rev.revsdk.statistic.Statistic;

import java.lang.reflect.Type;

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

public class StatisticSerializer implements JsonSerializer<Statistic> {
    @Override
    public JsonElement serialize(Statistic src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("app_name", src.getAppName());
        result.addProperty("sdk_key", src.getSDKKey());
        result.addProperty("sdk_version", src.getSDKVersion());
        result.addProperty("version", src.getVersion());
        result.add("carrier", new PhoneSerialize().serialize(src.getPhone(), Phone.class, context));
        return result;
    }
}
