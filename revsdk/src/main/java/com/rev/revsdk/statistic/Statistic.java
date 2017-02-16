package com.rev.revsdk.statistic;

import android.content.Context;

import com.rev.revsdk.config.Config;

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
