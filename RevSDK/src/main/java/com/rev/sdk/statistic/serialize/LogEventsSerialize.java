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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.sdk.statistic.sections.Event;
import com.rev.sdk.statistic.sections.LogEvents;

import java.lang.reflect.Type;

public class LogEventsSerialize implements JsonSerializer<LogEvents> {
    @Override
    public JsonElement serialize(LogEvents src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray result = new JsonArray();
        for(Event ev : src){
            JsonElement obj = new EventSerialize().serialize(ev,Event.class,context);
            result.add(obj);
        }
        return result;
    }
}
