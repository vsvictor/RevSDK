package com.rev.revsdk.statistic.serialize;

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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.sections.Device;

import java.lang.reflect.Type;

public class DeviceSerialize implements JsonSerializer<Device> {
    @Override
    public JsonElement serialize(Device src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("batt_cap", src.getBattCap());
        result.addProperty("batt_status", src.getBattStatus());
        result.addProperty("batt_tech", src.getBattTech());
        result.addProperty("batt_temp", src.getBattTemp());
        result.addProperty("batt_volt", src.getBattVolt());
        result.addProperty("brand", src.getBrand());
        result.addProperty("cpu", src.getCPU());
        result.addProperty("cpu_cores", src.getCPUCores());
        result.addProperty("cpu_freq", src.getCPUFreq());
        result.addProperty("cpu_number", src.getCPUNumber());
        result.addProperty("cpu_sub", src.getCPUSub());
        result.addProperty("device", src.getDevice());
        result.addProperty("hight", src.getHight());
        result.addProperty("iccid", src.getIccid());
        result.addProperty("imei", src.getIMEI());
        result.addProperty("imsi", src.getIMSI());
        result.addProperty("manufacture", src.getManufacture());
        result.addProperty("meis", src.getMEIS());
        result.addProperty("os", src.getOS());
        result.addProperty("phone_number", src.getPhoneNumber());
        result.addProperty("radio_serial", src.getRadioSerial());
        result.addProperty("serial_number", src.getSerialNumber());
        result.addProperty("uuid", src.getUUID());
        result.addProperty("width", src.getWidth());
        result.addProperty("os_name", src.getOSName());
        result.addProperty("os_version", src.getOSVersion());
        result.addProperty("model", src.getModel());
        return result;
    }
}
