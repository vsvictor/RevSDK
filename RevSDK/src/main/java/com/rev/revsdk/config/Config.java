package com.rev.revsdk.config;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.rev.revsdk.Data;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

public class Config extends Data implements Parcelable {
    @SerializedName("app_name")
    private String app_name;
    @SerializedName("os")
    private String os;
    @SerializedName("configs")
    private ConfigsList configs;

    private long lastUpdate;

    public Config(Parcel in) {
        String s = in.readString();
        Config c = RevSDK.gsonCreate().fromJson(s, Config.class);
        this.setAppName(c.getAppName());
        this.setOSName(c.getOSName());
        this.setParam(c.getParam());
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

    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("app_name", this.getAppName());
        result.put("os", this.getOSName());
        ConfigParamenetrs param = getParam().get(0);
        JsonObject obj = (JsonObject) RevSDK.gsonCreate().toJsonTree(param, ConfigParamenetrs.class);
        for (Map.Entry pair : obj.entrySet()) {
            result.put(pair.getKey().toString(), pair.getValue().toString());
        }
        return result;
    }

    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        Iterator it = this.toMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            result.add(new Pair(e.getKey().toString(), e.getValue().toString()));
        }
        return result;
    }

    public void setAppName(String appName) {
        this.app_name = appName;
    }

    public String getAppName() {
        return app_name;
    }

    private void setOSName(String osName) {
        this.os = osName;
    }

    public String getOSName() {
        return os;
    }

    public ConfigsList getParam() {
        return configs;
    }

    public void setParam(ConfigsList list) {
        this.configs = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String s = RevSDK.gsonCreate().toJson(this);
        dest.writeString(s);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Config createFromParcel(Parcel in) {
            return new Config(in);
        }

        public Config[] newArray(int size) {
            return new Config[size];
        }
    };
}
