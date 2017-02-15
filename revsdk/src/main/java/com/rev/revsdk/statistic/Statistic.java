package com.rev.revsdk.statistic;

import android.content.Context;

import com.rev.revsdk.config.Config;

/**
 * Created by victor on 05.02.17.
 */

public class Statistic {
    private String appName;
    private String sdkKey;
    private Context context;
    private Config config;
    private String version;
    private Phone phone;

    public Statistic(Context context, Config config){
        this.context = context;
        this.config = config;
        phone = new Phone(this.context);
        appName = config.getAppName();
    }

    public String getAppName() {
        return appName;
    }
    public String getSDKKey() {
        return sdkKey;
    }

    public void setSDKKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    public String getSDKVersion(){
        return config.getParam().get(0).getSdkReleaseVersion();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Phone getPhone() {
        return phone;
    }

}
