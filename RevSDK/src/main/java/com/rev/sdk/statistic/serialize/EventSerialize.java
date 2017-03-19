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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.sdk.statistic.sections.Event;

import java.lang.reflect.Type;

public class EventSerialize implements JsonSerializer<Event> {
    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("log_severity", src.getLogSeverity());
        result.addProperty("log_event_code", src.getLogEventCode());
        result.addProperty("log_message", src.getLogMessage());
        result.addProperty("log_interval", src.getLogInterval());
        result.addProperty("timestamp", src.getTimestamp());
        return null;
    }
}
