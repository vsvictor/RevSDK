package com.nuubit.sdk.statistic.sections;

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

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.types.Pair;

import java.util.ArrayList;

public class Network extends Data implements Parcelable {
    private Context context;

    private String cellularIPExternal;
    private String cellularIPInternal;
    private String DNS1;
    private String DNS2;
    private String ipReassemblies;
    private String ipTotalBytesIn;
    private String ipTotalBytesOut;
    private String ipTotalPacketsIn;
    private String ipTotalPacketsOut;
    private String rtt;
    private String tcpBytesIn;
    private String tcpBytesOut;
    private String tcpRetransmits;
    private String transportProtocol;
    private String udpBytesIn;
    private String udpBytesOut;
    private String wifiDHCP;
    private String wifiExtip;
    private String wifiGW;
    private String wifiIP;
    private String wifiMask;

    public Network(Context context) {
        this.context = context;

        this.cellularIPExternal = cellularIPExternal();
        this.cellularIPInternal = cellularIPInternal();
        this.DNS1 = DNS1();
        this.DNS2 = DNS2();
        this.ipReassemblies = ipReassemblies();
        this.ipTotalBytesIn = ipTotalBytesIn();
        this.ipTotalBytesOut = ipTotalBytesOut();
        this.ipTotalPacketsIn = ipTotalPacketsIn();
        this.ipTotalPacketsOut = ipTotalPacketsOut();
        this.rtt = rtt();
        this.tcpBytesIn = tcpBytesIn();
        this.tcpBytesOut = tcpBytesOut();
        this.tcpRetransmits = tcpRetransmits();
        this.transportProtocol = transportProtocol();
        this.udpBytesIn = udpBytesIn();
        this.udpBytesOut = udpBytesOut();
        this.wifiDHCP = wifiDHCP();
        this.wifiExtip = wifiExtip();
        this.wifiGW = wifiGW();
        this.wifiIP = wifiIP();
        this.wifiMask = wifiMask();

    }

    protected Network(Parcel in) {
        cellularIPExternal = in.readString();
        cellularIPInternal = in.readString();
        DNS1 = in.readString();
        DNS2 = in.readString();
        ipReassemblies = in.readString();
        ipTotalBytesIn = in.readString();
        ipTotalBytesOut = in.readString();
        ipTotalPacketsIn = in.readString();
        ipTotalPacketsOut = in.readString();
        rtt = in.readString();
        tcpBytesIn = in.readString();
        tcpBytesOut = in.readString();
        tcpBytesOut = in.readString();
        transportProtocol = in.readString();
        udpBytesIn = in.readString();
        udpBytesOut = in.readString();
        wifiDHCP = in.readString();
        wifiExtip = in.readString();
        wifiGW = in.readString();
        wifiIP = in.readString();
        wifiMask = in.readString();
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("cellularIPExternal", String.valueOf(cellularIPExternal)));
        result.add(new Pair("cellularIPInternal", String.valueOf(cellularIPInternal)));
        result.add(new Pair("DNS1", String.valueOf(DNS1)));
        result.add(new Pair("DNS2", String.valueOf(DNS2)));
        result.add(new Pair("ipReassemblies", String.valueOf(ipReassemblies)));
        result.add(new Pair("ipTotalBytesIn", String.valueOf(ipTotalBytesIn)));
        result.add(new Pair("ipTotalBytesOut", String.valueOf(ipTotalBytesOut)));
        result.add(new Pair("ipTotalPacketsIn", String.valueOf(ipTotalPacketsIn)));
        result.add(new Pair("ipTotalPacketsOut", String.valueOf(ipTotalPacketsOut)));
        result.add(new Pair("rtt", String.valueOf(rtt)));
        result.add(new Pair("tcpBytesIn", String.valueOf(tcpBytesIn)));
        result.add(new Pair("tcpBytesOut", String.valueOf(tcpBytesOut)));
        result.add(new Pair("tcpBytesOut", String.valueOf(tcpBytesOut)));
        result.add(new Pair("transportProtocol", String.valueOf(transportProtocol)));
        result.add(new Pair("udpBytesIn", String.valueOf(udpBytesIn)));
        result.add(new Pair("udpBytesOut", String.valueOf(udpBytesOut)));
        result.add(new Pair("wifiDHCP", String.valueOf(wifiDHCP)));
        result.add(new Pair("wifiExtip", String.valueOf(wifiExtip)));
        result.add(new Pair("wifiGW", String.valueOf(wifiGW)));
        result.add(new Pair("wifiIP", String.valueOf(wifiIP)));
        result.add(new Pair("wifiMask", String.valueOf(wifiMask)));
        return result;
    }

    public static final Creator<Network> CREATOR = new Creator<Network>() {
        @Override
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        @Override
        public Network[] newArray(int size) {
            return new Network[size];
        }
    };

    private String cellularIPExternal() {
        return NuubitConstants.UNDEFINED;
    }

    private String cellularIPInternal() {
        return NuubitConstants.UNDEFINED;
    }

    private String DNS1() {
        return NuubitConstants.GOOGLE_DNS_1;
    }

    private String DNS2() {
        return NuubitConstants.GOOGLE_DNS_2;
    }

    private String ipReassemblies() {
        return NuubitConstants.S_ZERO;
    }

    private String ipTotalBytesIn() {
        return NuubitConstants.S_ZERO;
    }

    private String ipTotalBytesOut() {
        return NuubitConstants.S_ZERO;
    }

    private String  ipTotalPacketsIn() {
        return NuubitConstants.S_ZERO;
    }

    private String  ipTotalPacketsOut() {
        return NuubitConstants.S_ZERO;
    }

    private String  rtt() {
        return NuubitConstants.S_ZERO;
    }

    private String tcpBytesIn() {
        return NuubitConstants.S_ZERO;
    }

    private String tcpBytesOut() {
        return NuubitConstants.S_ZERO;
    }

    private String tcpRetransmits() {
        return NuubitConstants.S_ZERO;
    }

    private String transportProtocol() {
        return ((NuubitApplication) context).getBest().toString();
    }

    private String udpBytesIn() {
        return NuubitConstants.S_ZERO;
    }

    private String udpBytesOut() {
        return NuubitConstants.S_ZERO;
    }

    private String wifiDHCP() {
        return NuubitConstants.UNDEFINED;
    }

    private String wifiExtip() {
        return NuubitConstants.UNDEFINED;
    }

    private String wifiGW() {
        return NuubitConstants.NET_IP;
    }

    private String wifiIP() {
        return NuubitConstants.IP;
    }

    private String wifiMask() {
        return NuubitConstants.MASK_24;
    }

    public String getCellularIPExternal() {
        return cellularIPExternal;
    }

    public String getCellularIPInternal() {
        return cellularIPInternal;
    }

    public String getDNS1() {
        return DNS1;
    }

    public String getDNS2() {
        return DNS2;
    }

    public String getIpReassemblies() {
        return ipReassemblies;
    }

    public String getIpTotalBytesIn() {
        return ipTotalBytesIn;
    }

    public String  getIpTotalBytesOut() {
        return ipTotalBytesOut;
    }

    public String getIpTotalPacketsIn() {
        return ipTotalPacketsIn;
    }

    public String getIpTotalPacketsOut() {
        return ipTotalPacketsOut;
    }

    public String  getRtt() {
        return rtt;
    }

    public String getTcpBytesIn() {
        return tcpBytesIn;
    }

    public String getTcpBytesOut() {
        return tcpBytesOut;
    }

    public String getTcpRetransmits() {
        return tcpRetransmits;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public String getUdpBytesIn() {
        return udpBytesIn;
    }

    public String getUdpBytesOut() {
        return udpBytesOut;
    }

    public String getWifiDHCP() {
        return wifiDHCP;
    }

    public String getWifiExtip() {
        return wifiExtip;
    }

    public String getWifiGW() {
        return wifiGW;
    }

    public String getWifiIP() {
        return wifiIP;
    }

    public String getWifiMask() {
        return wifiMask;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cellularIPExternal);
        dest.writeString(cellularIPInternal);
        dest.writeString(DNS1);
        dest.writeString(DNS2);
        dest.writeString(ipReassemblies);
        dest.writeString(ipTotalBytesIn);
        dest.writeString(ipTotalBytesOut);
        dest.writeString(ipTotalPacketsIn);
        dest.writeString(ipTotalPacketsOut);
        dest.writeString(rtt);
        dest.writeString(tcpBytesIn);
        dest.writeString(tcpBytesOut);
        dest.writeString(tcpRetransmits);
        dest.writeString(transportProtocol);
        dest.writeString(udpBytesIn);
        dest.writeString(udpBytesOut);
        dest.writeString(wifiDHCP);
        dest.writeString(wifiExtip);
        dest.writeString(wifiGW);
        dest.writeString(wifiIP);
        dest.writeString(wifiMask);
    }
}
