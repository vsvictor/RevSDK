package com.rev.revsdk.statistic.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.sections.Carrier;
import com.rev.revsdk.statistic.Statistic;
import com.rev.revsdk.statistic.sections.Device;
import com.rev.revsdk.statistic.sections.Location;
import com.rev.revsdk.statistic.sections.LogEvents;
import com.rev.revsdk.statistic.sections.Network;
import com.rev.revsdk.statistic.sections.Requests;
import com.rev.revsdk.statistic.sections.WiFi;

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
        result.add("carrier", new CarrierSerialize().serialize(src.getCarrier(), Carrier.class, context));
        result.add("device", new DeviceSerialize().serialize(src.getDevice(), Device.class, context));
        result.add("log_events", new LogEventsSerialize().serialize(src.getEvents(), LogEvents.class, context));
        result.add("location", new LocationSerialize().serialize(src.getLocation(), Location.class, context));
        result.add("network", new NetworkSerialize().serialize(src.getNetwork(), Network.class, context));
        result.add("requests", new RequestsSerialize().serialize(src.getRequests(), Requests.class, context));
        result.add("wifi", new WiFiSerialize().serialize(src.getWifi(), WiFi.class, context));
        return result;
    }
}
