package com.rev.revsdk.config.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.config.OperationMode;

import java.lang.reflect.Type;

/**
 * Created by victor on 03.02.17.
 */

public class OperationModeSerialize implements JsonSerializer<OperationMode> {
    @Override
    public JsonElement serialize(OperationMode src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        String res = "";
        if(src == OperationMode.transfer_and_report) res="transfer_and_report";
        else if(src == OperationMode.transfer_only) res = "transfer_only";
        else if(src == OperationMode.report_only) res = "report_only";
        else res = "off";
        result.addProperty("operation_mode", res);
        return result;
    }
}
