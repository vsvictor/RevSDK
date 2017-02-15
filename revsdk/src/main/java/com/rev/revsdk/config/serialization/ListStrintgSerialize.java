package com.rev.revsdk.config.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.config.ListString;

import java.lang.reflect.Type;

/**
 * Created by victor on 03.02.17.
 */

public class ListStrintgSerialize implements JsonSerializer<ListString>{
    @Override
    public JsonElement serialize(ListString src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray arr = new JsonArray();
        for(String s : src){
            arr.add(s);
        }
        return arr;
    }
}
