package com.rev.revsdk.statistic.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.sections.Carrier;

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
public class CarrierSerialize implements JsonSerializer<Carrier>{
    @Override
    public JsonElement serialize(Carrier src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        if(src != null) {
            result.addProperty("country_code", src.getCountryCode());
            result.addProperty("device_id", src.getDeviceID());
            result.addProperty("mcc", src.getMCC());
            result.addProperty("mnc", src.getMNC());
            result.addProperty("net_operator", src.getNetOperator());
            result.addProperty("network_type", src.getNetType());
            result.addProperty("rssi", src.getRSSI());
            result.addProperty("rssi_avg", src.getRSSIAverage());
            result.addProperty("rssi_best", src.getRSSIBest());
            result.addProperty("signal_type", src.getNetworkType());
            result.addProperty("sim_operator", src.getSimOperator());
            result.addProperty("tower_cell_id_l", src.getTowerLong());
            result.addProperty("tower_cell_id_s", src.getTowerShort());
        }

        return result;
    }
}
