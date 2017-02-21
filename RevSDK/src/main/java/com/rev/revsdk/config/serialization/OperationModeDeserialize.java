package com.rev.revsdk.config.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rev.revsdk.config.OperationMode;

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
