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

import com.rev.revsdk.protocols.Protocol;

public class RequestOne {
    private long id = -1L;
    private int connectionID;
    private String contentEncode;
    private String contentType;
    private long startTS;
    private long endTS;
    private long firstByteTime;
    private int keepAliveStatus;
    private String localCacheStatus;
    private String method;
    private String network;
    private Protocol protocol;
    private long receivedBytes;
    private long sentBytes;
    private int statusCode;
    private int successStatus;
    private Protocol transportProtocol;
    private String url;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public int getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
    }

    public String getContentEncode() {
        return contentEncode;
    }

    public void setContentEncode(String contentEncode) {
        this.contentEncode = contentEncode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public long getFirstByteTime() {
        return firstByteTime;
    }

    public void setFirstByteTime(long firstByteTime) {
        this.firstByteTime = firstByteTime;
    }

    public int getKeepAliveStatus() {
        return keepAliveStatus;
    }

    public void setKeepAliveStatus(int keepAliveStatus) {
        this.keepAliveStatus = keepAliveStatus;
    }

    public String getLocalCacheStatus() {
        return localCacheStatus;
    }

    public void setLocalCacheStatus(String localCacheStatus) {this.localCacheStatus = localCacheStatus;}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public void setReceivedBytes(long receivedBytes) {
        this.receivedBytes = receivedBytes;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public void setSentBytes(long sentBytes) {
        this.sentBytes = sentBytes;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(int successStatus) {
        this.successStatus = successStatus;
    }

    public Protocol getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(Protocol transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }
}
