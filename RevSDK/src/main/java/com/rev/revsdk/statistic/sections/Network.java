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
import com.rev.revsdk.protocols.Protocol;

public class Network {
    private Context context;

    private String cellularIPExternal;
    private String cellularIPInternal;
    private String DNS1;
    private String DNS2;
    private int ipReassemblies;
    private long ipTotalBytesIn;
    private long ipTotalBytesOut;
    private long ipTotalPacketsIn;
    private long ipTotalPacketsOut;
    private int rtt;
    private int tcpBytesIn;
    private int tcpBytesOut;
    private int tcpRetransmits;
    private String transportProtocol;
    private long udpBytesIn;
    private long udpBytesOut;
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
        return Constants.zerro_ip;
    }

    private String cellularIPInternal() {
        return Constants.zerro_ip;
    }

    private String DNS1() {
        return Constants.zerro_ip;
    }

    private String DNS2() {
        return Constants.zerro_ip;
    }

    private int ipReassemblies() {
        return 0;
    }

    private long ipTotalBytesIn() {
        return 0;
    }

    private long ipTotalBytesOut() {
        return 0;
    }

    private long ipTotalPacketsIn() {
        return 0;
    }

    private long ipTotalPacketsOut() {
        return 0;
    }

    private int rtt() {
        return 0;
    }

    private int tcpBytesIn() {
        return 0;
    }

    private int tcpBytesOut() {
        return 0;
    }

    private int tcpRetransmits() {
        return 0;
    }

    private String transportProtocol() {
        return Protocol.UNDEFINED.toString();
    }

    private long udpBytesIn() {
        return 0;
    }

    private long udpBytesOut() {
        return 0;
    }

    private String wifiDHCP() {
        return Constants.zerro_ip;
    }

    private String wifiExtip() {
        return Constants.zerro_ip;
    }

    private String wifiGW() {
        return Constants.zerro_ip;
    }

    private String wifiIP() {
        return Constants.zerro_ip;
    }

    private String wifiMask() {
        return Constants.zerro_ip;
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

    public int getIpReassemblies() {
        return ipReassemblies;
    }

    public long getIpTotalBytesIn() {
        return ipTotalBytesIn;
    }

    public long getIpTotalBytesOut() {
        return ipTotalBytesOut;
    }

    public long getIpTotalPacketsIn() {
        return ipTotalPacketsIn;
    }

    public long getIpTotalPacketsOut() {
        return ipTotalPacketsOut;
    }

    public int getRtt() {
        return rtt;
    }

    public int getTcpBytesIn() {
        return tcpBytesIn;
    }

    public int getTcpBytesOut() {
        return tcpBytesOut;
    }

    public int getTcpRetransmits() {
        return tcpRetransmits;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public long getUdpBytesIn() {
        return udpBytesIn;
    }

    public long getUdpBytesOut() {
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
