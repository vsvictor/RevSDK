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
import android.os.Parcel;
import android.os.Parcelable;

import com.rev.revsdk.Constants;
import com.rev.revsdk.Data;
import com.rev.revsdk.utils.Pair;

import java.util.ArrayList;

public class WiFi extends Data implements Parcelable {
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

    protected WiFi(Parcel in) {
        mac = in.readString();
        ssid = in.readString();
        wifi_enc = in.readString();
        wifi_freq = in.readString();
        wifi_rssi = in.readString();
        wifi_rssibest = in.readString();
        wifi_sig = in.readString();
        wifi_speed = in.readString();
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("mac", String.valueOf(mac)));
        result.add(new Pair("ssid", String.valueOf(ssid)));
        result.add(new Pair("wifi_enc", String.valueOf(wifi_enc)));
        result.add(new Pair("wifi_freq", String.valueOf(wifi_freq)));
        result.add(new Pair("wifi_rssi", String.valueOf(wifi_rssi)));
        result.add(new Pair("wifi_rssibest", String.valueOf(wifi_rssibest)));
        result.add(new Pair("wifi_sig", String.valueOf(wifi_sig)));
        result.add(new Pair("wifi_speed", String.valueOf(wifi_speed)));
        return result;
    }

    public static final Creator<WiFi> CREATOR = new Creator<WiFi>() {
        @Override
        public WiFi createFromParcel(Parcel in) {
            return new WiFi(in);
        }

        @Override
        public WiFi[] newArray(int size) {
            return new WiFi[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mac);
        dest.writeString(ssid);
        dest.writeString(wifi_enc);
        dest.writeString(wifi_freq);
        dest.writeString(wifi_rssi);
        dest.writeString(wifi_rssibest);
        dest.writeString(wifi_sig);
        dest.writeString(wifi_speed);
    }
}
