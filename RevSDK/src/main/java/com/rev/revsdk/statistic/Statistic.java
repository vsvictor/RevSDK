package com.rev.revsdk.statistic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;

import com.google.gson.annotations.SerializedName;
import com.rev.revsdk.RevApplication;
import com.rev.revsdk.config.Config;
import com.rev.revsdk.permission.RequestUserPermission;
import com.rev.revsdk.statistic.sections.App;
import com.rev.revsdk.statistic.sections.Carrier;
import com.rev.revsdk.statistic.sections.Device;
import com.rev.revsdk.statistic.sections.Location;
import com.rev.revsdk.statistic.sections.LogEvents;
import com.rev.revsdk.statistic.sections.Network;
import com.rev.revsdk.statistic.sections.Requests;
import com.rev.revsdk.statistic.sections.WiFi;

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
    private Context context;

    @SerializedName("version")
    private String version;
    @SerializedName("app_name")
    private String appName;
    @SerializedName("sdk_key")
    private String sdkKey;
    @SerializedName("sdk_version")
    private String sdkVersion;
    @SerializedName("carrier")
    private Carrier carrier;
    @SerializedName("device")
    private Device device;
    @SerializedName("log_events")
    private LogEvents events;
    @SerializedName("location")
    private Location location;
    @SerializedName("network")
    private Network network;
    @SerializedName("requests")
    private Requests requests;
    @SerializedName("wifi")
    private WiFi wifi;

    public Statistic(Context context){
        App app = new App();
        setVersion(app.getVersion());
        setAppName(app.getAppName());
        setSDKKey(app.getSDKKey());
        setSDKVersion(app.getSDKVersion());

        carrier = new Carrier(this.context);
        device = new Device(context);
        events = new LogEvents(context);
        location = new Location(context);
        network = new Network(context);
        requests = new Requests(context);
        wifi = new WiFi(context);
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

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public LogEvents getEvents() {
        return events;
    }

    public void setEvents(LogEvents events) {
        this.events = events;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    public WiFi getWifi() {
        return wifi;
    }

    public void setWifi(WiFi wifi) {
        this.wifi = wifi;
    }
}
