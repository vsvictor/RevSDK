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
import com.nuubit.sdk.statistic.sections.WiFi;

import java.lang.reflect.Type;

public class WiFiSerialize implements JsonSerializer<WiFi> {
    @Override
    public JsonElement serialize(WiFi src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("mac", src.getMac());
        result.addProperty("ssid", src.getMac());
        result.addProperty("wifi_enc", src.getMac());
        result.addProperty("wifi_freq", src.getMac());
        result.addProperty("wifi_rssi", src.getMac());
        result.addProperty("wifi_rssibest", src.getMac());
        result.addProperty("wifi_sig", src.getMac());
        result.addProperty("wifi_speed", src.getMac());

        return result;
    }
}
