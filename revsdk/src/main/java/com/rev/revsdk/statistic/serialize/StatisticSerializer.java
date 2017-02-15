package com.rev.revsdk.statistic.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.Phone;
import com.rev.revsdk.statistic.Statistic;

import java.lang.reflect.Type;

/**
 * Created by victor on 05.02.17.
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
