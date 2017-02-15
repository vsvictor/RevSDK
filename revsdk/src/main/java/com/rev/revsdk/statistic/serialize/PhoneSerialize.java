package com.rev.revsdk.statistic.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.Phone;

import java.lang.reflect.Type;

/**
 * Created by victor on 05.02.17.
 */
public class PhoneSerialize implements JsonSerializer<Phone>{
    @Override
    public JsonElement serialize(Phone src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("country_code", src.getCountryCode());
        result.addProperty("device_id", src.getDeviceID());
        result.addProperty("mcc", src.getMCC());
        result.addProperty("mnc", src.getMNC());
        result.addProperty("net_operator", src.getNetOperator());
        result.addProperty("network_type", src.getNetType());
        result.addProperty("phone_type",src.getPhoneType());
        result.addProperty("rssi", src.getRSSI());
        result.addProperty("rssi_avg", src.getRSSIAverage());
        result.addProperty("rssi_best", src.getRSSIBest());
        result.addProperty("signal_type", src.getNetworkType());
        result.addProperty("sim_operator", src.getSimOperator());
        return result;
    }
}
