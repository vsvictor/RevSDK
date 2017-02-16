package com.rev.revsdk.config;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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

public class Config {
    @SerializedName("app_name")
    private String app_name;
    @SerializedName("os")
    private String os;
    @SerializedName("configs")
    private ConfigsList configs;

    private long lastUpdate;


    public ConfigsList getParam(){return  configs;}

    public String getAppName() {
        return app_name;
    }

    public void save(Gson gson, SharedPreferences share) {
        String s = gson.toJson(this);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("config", s);
        editor.apply();
    }
    public static Config load(Gson gson, SharedPreferences share){
        String s = share.getString("config", "");
        if(!s.isEmpty()){
            return gson.fromJson(s, Config.class);
        }
        else return  null;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
