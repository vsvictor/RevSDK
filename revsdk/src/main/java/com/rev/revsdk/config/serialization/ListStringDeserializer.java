package com.rev.revsdk.config.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rev.revsdk.config.ListString;

import java.lang.reflect.Type;

/**
 * Created by victor on 03.02.17.
 */

public class ListStringDeserializer implements JsonDeserializer<ListString>{
    @Override
    public ListString deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ListString result  = new ListString();
        JsonArray arr = json.getAsJsonArray();
        for(JsonElement elem : arr){
            String s = elem.getAsString();
            result.add(s);
        }
        return result;
    }
}
