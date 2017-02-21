package com.rev.revsdk.statistic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;

import com.rev.revsdk.config.Config;
import com.rev.revsdk.permission.RequestUserPermission;
import com.rev.revsdk.statistic.sections.Carrier;

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<Section> sections;

    private String appName;
    private String sdkKey;
    private Context context;
    private Config config;
    private String version;
    private Carrier carrier;

    public Statistic(Context context, Config config){
        this.context = context;
        this.config = config;
        //this.carrier = new Carrier(this.context);
        this.appName = config.getAppName();
        this.sections = new ArrayList<>();
    }

    public void addSection(Section section){
        if(sections.contains(section)) return;
        switch (section){
            case MAIN:{
                sections.add(Section.MAIN);
                break;
            }
            case CARRIER:{
                boolean p = checkPermission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE);
                if(p) sections.add(Section.CARRIER);
                break;
            }
            case DEVICE:{break;}
            case EVENTS:{break;}
            case LOCATION:{break;}
            case NETWORK:{break;}
            case REQUEST:{
                boolean p = checkPermission(Manifest.permission.INTERNET);
                if(p) sections.add(Section.CARRIER);
                break;
            }
            case WIFI:{break;}
        }
    }

    private boolean checkPermission(String... permission){
        boolean result = true;
        for (String s : permission){
            int perm = ActivityCompat.checkSelfPermission(this.context, s);
            result = result && ( perm == PackageManager.PERMISSION_GRANTED);
        }
        return result;
    }

    public ArrayList<Section> getSections() {
        return sections;
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

    public Carrier getCarrier() {
        return carrier;
    }
}
