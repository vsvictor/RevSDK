package com.rev.revsdk.config;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by victor on 03.02.17.
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
