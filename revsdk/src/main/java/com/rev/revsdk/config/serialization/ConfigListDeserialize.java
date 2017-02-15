package com.rev.revsdk.config.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rev.revsdk.config.ConfigParamenetrs;
import com.rev.revsdk.config.ConfigsList;

import java.lang.reflect.Type;

/**
 * Created by victor on 03.02.17.
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
