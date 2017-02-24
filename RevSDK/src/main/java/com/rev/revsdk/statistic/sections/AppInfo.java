package com.rev.revsdk.statistic.sections;

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

import android.content.Context;

import com.rev.revsdk.RevApplication;

public class AppInfo {
    private Context context;

    private String masterAppName;
    private String masterAppBuild;
    private String masterAppBuildID;
    private String masterAppVersion;

    public AppInfo(Context context){
        this.context = context;
        this.masterAppName = masterAppName();
        this.masterAppBuild = masterAppBuild();
        this.masterAppBuildID = masterAppBuildID();
        this.masterAppVersion = masterAppVersion();
    }

    private String masterAppName(){return ((RevApplication)context).getConfig().getAppName();}
    private String masterAppBuild(){return ((RevApplication)context).getVersion();}
    private String masterAppBuildID(){return ((RevApplication)context).getPackage();}
    private String masterAppVersion(){return ((RevApplication)context).getVersion();}

    public String getMasterAppName() {
        return masterAppName;
    }

    public String getMasterAppBuild() {
        return masterAppBuild;
    }

    public String getMasterAppBuildID() {
        return masterAppBuildID;
    }

    public String getMasterAppVersion() {
        return masterAppVersion;
    }
}
