package com.nuubit.sdk.config.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuubit.sdk.config.ConfigParamenetrs;
import com.nuubit.sdk.config.ConfigsList;

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

public class ConfigListDeserialize implements JsonDeserializer<ConfigsList> {
    @Override
    public ConfigsList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ConfigsList config = new ConfigsList();
        JsonArray arr = json.getAsJsonArray();
        ConfigParametersDeserialize deser = new ConfigParametersDeserialize();
        for(JsonElement elem : arr){
            JsonObject obj = elem.getAsJsonObject();
            ConfigParamenetrs current = deser.deserialize(obj, ConfigParamenetrs.class, context);
            config.add(current);
        }
        return config;
    }
}
