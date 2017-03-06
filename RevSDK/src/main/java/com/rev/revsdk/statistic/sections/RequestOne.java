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

import android.os.Parcel;
import android.os.Parcelable;

import com.rev.revsdk.Data;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.utils.Pair;

import java.util.ArrayList;

public class RequestOne extends Data implements Parcelable {
    private long id;
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
    private String destination;
    private String xRevCach;
    private String domain;
    private Protocol edge_transport;

    public RequestOne() {

    }

    protected RequestOne(Parcel in) {
        id = in.readLong();
        connectionID = in.readInt();
        contentEncode = in.readString();
        contentType = in.readString();
        startTS = in.readLong();
        endTS = in.readLong();
        firstByteTime = in.readLong();
        keepAliveStatus = in.readInt();
        localCacheStatus = in.readString();
        method = in.readString();
        network = in.readString();
        receivedBytes = in.readLong();
        sentBytes = in.readLong();
        statusCode = in.readInt();
        successStatus = in.readInt();
        url = in.readString();
        destination = in.readString();
        xRevCach = in.readString();
        domain = in.readString();
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("id", String.valueOf(id)));
        result.add(new Pair("connectionID", String.valueOf(connectionID)));
        result.add(new Pair("contentEncode", String.valueOf(contentEncode)));
        result.add(new Pair(contentType, String.valueOf(contentType)));
        result.add(new Pair("startTS", String.valueOf(startTS)));
        result.add(new Pair("endTS", String.valueOf(endTS)));
        result.add(new Pair("firstByteTime", String.valueOf(firstByteTime)));
        result.add(new Pair("keepAliveStatus", String.valueOf(keepAliveStatus)));
        result.add(new Pair("localCacheStatus", String.valueOf(localCacheStatus)));
        result.add(new Pair("method", String.valueOf(method)));
        result.add(new Pair("network", String.valueOf(network)));
        result.add(new Pair("receivedBytes", String.valueOf(receivedBytes)));
        result.add(new Pair("sentBytes", String.valueOf(sentBytes)));
        result.add(new Pair("statusCode", String.valueOf(statusCode)));
        result.add(new Pair("successStatus", String.valueOf(successStatus)));
        result.add(new Pair("url", String.valueOf(url)));
        result.add(new Pair("destination", String.valueOf(destination)));
        result.add(new Pair("xRevCach", String.valueOf(xRevCach)));
        result.add(new Pair("domain", String.valueOf(domain)));
        return result;
    }

    public static final Creator<RequestOne> CREATOR = new Creator<RequestOne>() {
        @Override
        public RequestOne createFromParcel(Parcel in) {
            return new RequestOne(in);
        }

        @Override
        public RequestOne[] newArray(int size) {
            return new RequestOne[size];
        }
    };

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getXRevCach() {
        return xRevCach;
    }

    public void setXRevCache(String xRevCach) {
        this.xRevCach = xRevCach;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Protocol getEdgeTransport() {
        return edge_transport;
    }

    public void setEdgeTransport(Protocol edge_transport) {
        this.edge_transport = edge_transport;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(connectionID);
        dest.writeString(contentEncode);
        dest.writeString(contentType);
        dest.writeLong(startTS);
        dest.writeLong(endTS);
        dest.writeLong(firstByteTime);
        dest.writeInt(keepAliveStatus);
        dest.writeString(localCacheStatus);
        dest.writeString(method);
        dest.writeString(network);
        dest.writeLong(receivedBytes);
        dest.writeLong(sentBytes);
        dest.writeInt(statusCode);
        dest.writeInt(successStatus);
        dest.writeString(url);
        dest.writeString(destination);
        dest.writeString(xRevCach);
        dest.writeString(domain);
    }
}
