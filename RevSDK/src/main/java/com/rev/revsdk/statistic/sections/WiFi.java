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

import com.rev.revsdk.Constants;

public class WiFi {
    private Context context;

    private String mac;
    private String ssid;
    private String wifi_enc;
    private String wifi_freq;
    private String wifi_rssi;
    private String wifi_rssibest;
    private String wifi_sig;
    private String wifi_speed;

    public WiFi(Context context) {
        this.context = context;

        this.mac = mac();
        this.ssid = ssid();
        this.wifi_enc = wifi_enc();
        this.wifi_freq = wifi_freq();
        this.wifi_rssi = wifi_rssi();
        this.wifi_rssibest = wifi_rssibest();
        this.wifi_sig = wifi_sig();
        this.wifi_speed = wifi_speed();

    }

    private String mac() {
        return Constants.UNDEFINED;
    }

    private String ssid() {
        return Constants.UNDEFINED;
    }

    private String wifi_enc() {
        return Constants.UNDEFINED;
    }

    private String wifi_freq() {
        return Constants.UNDEFINED;
    }

    private String wifi_rssi() {
        return Constants.UNDEFINED;
    }

    private String wifi_rssibest() {
        return Constants.UNDEFINED;
    }

    private String wifi_sig() {
        return Constants.UNDEFINED;
    }

    private String wifi_speed() {
        return Constants.UNDEFINED;
    }

    public String getMac() {
        return mac;
    }

    public String getSsid() {
        return ssid;
    }

    public String getWifi_enc() {
        return wifi_enc;
    }

    public String getWifi_freq() {
        return wifi_freq;
    }

    public String getWifi_rssi() {
        return wifi_rssi;
    }

    public String getWifi_rssibest() {
        return wifi_rssibest;
    }

    public String getWifi_sig() {
        return wifi_sig;
    }

    public String getWifi_speed() {
        return wifi_speed;
    }
}
