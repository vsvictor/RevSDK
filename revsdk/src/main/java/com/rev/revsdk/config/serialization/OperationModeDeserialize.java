package com.rev.revsdk.config.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rev.revsdk.config.OperationMode;

import java.lang.reflect.Type;

/**
 * Created by victor on 03.02.17.
 */

public class OperationModeDeserialize implements JsonDeserializer<OperationMode>{
    @Override
    public OperationMode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(!json.isJsonPrimitive()) {
            JsonObject obj = (JsonObject) json.getAsJsonObject();
            if (obj.get("operation_mode").getAsString().equals("transfer_and_report"))
                return OperationMode.transfer_and_report;
            else if (obj.get("operation_mode").getAsString().equals("transfer_only"))
                return OperationMode.transfer_only;
            else if (obj.get("operation_mode").getAsString().equals("report_only"))
                return OperationMode.report_only;
            else return OperationMode.off;
        }
        else {
            String sMode = json.getAsString();
            if(sMode.equals("transfer_and_report")) return OperationMode.transfer_and_report;
            else if(sMode.equals("transfer_only")) return OperationMode.transfer_only;
            else if(sMode.equals("report_only")) return OperationMode.report_only;
            else return OperationMode.off;
        }
    }
}
