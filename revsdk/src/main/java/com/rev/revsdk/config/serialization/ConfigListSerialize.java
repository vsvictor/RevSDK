package com.rev.revsdk.config.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.config.ConfigParamenetrs;
import com.rev.revsdk.config.ConfigsList;

import java.lang.reflect.Type;

/**
 * Created by victor on 03.02.17.
 */

public class ConfigListSerialize implements JsonSerializer<ConfigsList> {
    @Override
    public JsonElement serialize(ConfigsList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray arr = new JsonArray();
        ConfigParametersSerialize ser = new ConfigParametersSerialize();

        for(ConfigParamenetrs params : src){
            arr.add(ser.serialize(params, ConfigParamenetrs.class, context));
        }
        return arr;
    }
}
