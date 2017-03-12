package com.rev.revsdk.statistic;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.rev.revsdk.Constants;
import com.rev.revsdk.statistic.sections.App;
import com.rev.revsdk.statistic.sections.AppInfo;
import com.rev.revsdk.statistic.sections.Carrier;
import com.rev.revsdk.statistic.sections.Data;
import com.rev.revsdk.statistic.sections.Device;
import com.rev.revsdk.statistic.sections.Location;
import com.rev.revsdk.statistic.sections.LogEvents;
import com.rev.revsdk.statistic.sections.Network;
import com.rev.revsdk.statistic.sections.Requests;
import com.rev.revsdk.statistic.sections.WiFi;
import com.rev.revsdk.types.Pair;

import java.util.ArrayList;

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

public class Statistic extends Data implements Parcelable {
    private Context context;

    @SerializedName("version")
    private String version;
    @SerializedName("app_name")
    private String appName;
    @SerializedName("sdk_key")
    private String sdkKey;
    @SerializedName("sdk_version")
    private String sdkVersion;
    @SerializedName("a_b_mode")
    private boolean abMode;
    @SerializedName("IP")
    private String ip;
    @SerializedName("hits")
    private int hits;
    @SerializedName("start_ts")
    private long startTS;
    @SerializedName("end_ts")
    private long endTS;
    @SerializedName("level")
    private String level;
    @SerializedName("carrier")
    private Carrier carrier;
    @SerializedName("application_info")
    private AppInfo appInfo;
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
        this.context = context;

        App app = new App();
        setVersion(app.getVersion());
        setAppName(app.getAppName());
        setSDKKey(app.getSDKKey());
        setSDKVersion(app.getSDKVersion());
        setABMode(false);
        setIP(Constants.LOCAL_IP);
        setHits(Constants.HITS);
        setStartTS(Constants.BIG_NUMBER_1);
        setEndTS(Constants.BIG_NUMBER_2);
        setLevel(Constants.LEVEL);

        carrier = new Carrier(this.context);
        device = new Device(this.context);
        events = new LogEvents(this.context);
        location = new Location(this.context);
        network = new Network(this.context);
        requests = new Requests(this.context);
        wifi = new WiFi(this.context);
    }

    protected Statistic(Parcel in) {
        super(in);
        version = in.readString();
        appName = in.readString();
        sdkKey = in.readString();
        sdkVersion = in.readString();
        abMode = in.readByte() != 0;
        ip = in.readString();
        hits = in.readInt();
        startTS = in.readLong();
        endTS = in.readLong();
        level = in.readString();
        carrier = in.readParcelable(Carrier.class.getClassLoader());
        appInfo = in.readParcelable(AppInfo.class.getClassLoader());
        device = in.readParcelable(Device.class.getClassLoader());
        location = in.readParcelable(Location.class.getClassLoader());
        network = in.readParcelable(Network.class.getClassLoader());
        wifi = in.readParcelable(WiFi.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(version);
        dest.writeString(appName);
        dest.writeString(sdkKey);
        dest.writeString(sdkVersion);
        dest.writeByte((byte) (abMode ? 1 : 0));
        dest.writeString(ip);
        dest.writeInt(hits);
        dest.writeLong(startTS);
        dest.writeLong(endTS);
        dest.writeString(level);
        dest.writeParcelable(carrier, flags);
        dest.writeParcelable(appInfo, flags);
        dest.writeParcelable(device, flags);
        dest.writeParcelable(location, flags);
        dest.writeParcelable(network, flags);
        dest.writeParcelable(wifi, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Statistic> CREATOR = new Creator<Statistic>() {
        @Override
        public Statistic createFromParcel(Parcel in) {
            return new Statistic(in);
        }

        @Override
        public Statistic[] newArray(int size) {
            return new Statistic[size];
        }
    };

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

    public boolean isABMode() {
        return abMode;
    }

    public void setABMode(boolean mode) {
        this.abMode = mode;
    }

    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public long getStartTS() {
        return startTS;
    }

    public void setStartTS(long startTS) {
        this.startTS = startTS;
    }

    public long getEndTS() {
        return endTS;
    }

    public void setEndTS(long endTS) {
        this.endTS = endTS;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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


    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("version", String.valueOf(version)));
        result.add(new Pair("appName", String.valueOf(appName)));
        result.add(new Pair("sdkKey", String.valueOf(sdkKey)));
        result.add(new Pair("sdkVersion", String.valueOf(sdkVersion)));
        result.add(new Pair("abMode", String.valueOf(abMode)));
        result.add(new Pair("ip", String.valueOf(ip)));
        result.add(new Pair("hits", String.valueOf(hits)));
        result.add(new Pair("startTS", String.valueOf(startTS)));
        result.add(new Pair("endTS", String.valueOf(endTS)));
        result.add(new Pair("level", String.valueOf(level)));
        return result;
    }
}
