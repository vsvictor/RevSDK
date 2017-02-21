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

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.rev.revsdk.RevApplication;

public class App {
    private long id;
    private String  version;
    private String appName;
    private String sdkKey;
    private String sdkVersion;

    public App(){
        id();
        version();
        appName();
        sdkKey();
        sdkVersion();
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSDKKey() {
        return sdkKey;
    }

    public void setSDKKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    public String getSDKVersion() {
        return sdkVersion;
    }

    public void setSDKVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    private void id(){ this.id = 0;}
    private void version(){
        PackageInfo pInfo = null;
        try {
            pInfo = RevApplication.getInstance().getPackageManager().getPackageInfo(RevApplication.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            pInfo = null;
            //e.printStackTrace();
        }
        if(pInfo != null) this.version = pInfo.versionName;
        else this.version = "1.0";
    }
    private void appName(){
        this.appName = RevApplication.getInstance().getConfig().getAppName();
    }
    private void sdkKey(){
        this.sdkKey = RevApplication.getInstance().getSDKKey();
    }
    private void sdkVersion(){
        this.sdkVersion = RevApplication.getInstance().getConfig().getParam().get(0).getSdkReleaseVersion();
    }
}
