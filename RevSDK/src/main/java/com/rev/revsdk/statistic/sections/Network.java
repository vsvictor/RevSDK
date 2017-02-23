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
import com.rev.revsdk.RevApplication;
import com.rev.revsdk.protocols.Protocol;

public class Network {
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

    private String cellularIPExternal() {
        return Constants.undefined;
    }

    private String cellularIPInternal() {
        return Constants.undefined;
    }

    private String DNS1() {
        return Constants.google_dns1;
    }

    private String DNS2() {
        return Constants.google_dns2;
    }

    private String ipReassemblies() {
        return Constants.s_zerro;
    }

    private String ipTotalBytesIn() {
        return Constants.s_zerro;
    }

    private String ipTotalBytesOut() {
        return Constants.s_zerro;
    }

    private String  ipTotalPacketsIn() {
        return Constants.s_zerro;
    }

    private String  ipTotalPacketsOut() {
        return Constants.s_zerro;
    }

    private String  rtt() {
        return Constants.s_zerro;
    }

    private String tcpBytesIn() {
        return Constants.s_zerro;
    }

    private String tcpBytesOut() {
        return Constants.s_zerro;
    }

    private String tcpRetransmits() {
        return Constants.s_zerro;
    }

    private String transportProtocol() {
        return ((RevApplication)context).getBest().toString();
    }

    private String udpBytesIn() {
        return Constants.s_zerro;
    }

    private String udpBytesOut() {
        return Constants.s_zerro;
    }

    private String wifiDHCP() {
        return Constants.undefined;
    }

    private String wifiExtip() {
        return Constants.undefined;
    }

    private String wifiGW() {
        return Constants.net_ip;
    }

    private String wifiIP() {
        return Constants.ip;
    }

    private String wifiMask() {
        return Constants.mask_24;
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
}
