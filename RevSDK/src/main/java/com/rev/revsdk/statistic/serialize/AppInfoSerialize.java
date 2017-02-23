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

import android.content.pm.ApplicationInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.revsdk.statistic.sections.AppInfo;


import java.lang.reflect.Type;

public class AppInfoSerialize implements JsonSerializer<AppInfo> {
    @Override
    public JsonElement serialize(AppInfo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("master_app_name", src.getMasterAppName());
        result.addProperty("master_app_build", src.getMasterAppBuild());
        result.addProperty("master_app_bundle_id", src.getMasterAppBuildID());
        result.addProperty("master_app_version", src.getMasterAppVersion());

        return result;
    }
}
