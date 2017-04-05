package com.rev.sdk.statistic.sections;

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

import com.rev.sdk.Constants;
import com.rev.sdk.types.Pair;

import java.util.ArrayList;

public class LogEvent extends Data implements Parcelable {
    private String logSeverity;
    private String logEventCode;
    private String logMessage;
    private float logInterval;
    private long timestamp;

    public LogEvent() {
        this.logSeverity = logSeverity();
        this.logEventCode = logEventCode();
        this.logMessage = logMessage();
        this.logInterval = logInterval();
        this.timestamp = timestamp();
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("logSeverity", String.valueOf(logSeverity)));
        result.add(new Pair("logEventCode", String.valueOf(logEventCode)));
        result.add(new Pair("logMessage", String.valueOf(logMessage)));
        result.add(new Pair("logInterval", String.valueOf(logInterval)));
        result.add(new Pair("timestamp", String.valueOf(timestamp)));
        return result;
    }

    protected LogEvent(Parcel in) {
        logSeverity = in.readString();
        logEventCode = in.readString();
        logMessage = in.readString();
        logInterval = in.readFloat();
        timestamp = in.readLong();
    }

    public static final Creator<LogEvent> CREATOR = new Creator<LogEvent>() {
        @Override
        public LogEvent createFromParcel(Parcel in) {
            return new LogEvent(in);
        }

        @Override
        public LogEvent[] newArray(int size) {
            return new LogEvent[size];
        }
    };

    private String logSeverity(){
        return Constants.UNDEFINED;
    }
    private String logEventCode(){
        return Constants.UNDEFINED;
    }
    private String logMessage(){
        return Constants.UNDEFINED;
    }
    private float logInterval(){
        return 0;
    }
    private long timestamp(){
        return 0;
    }

    public String getLogSeverity() {
        return logSeverity;
    }

    public String getLogEventCode() {
        return logEventCode;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public float getLogInterval() {
        return logInterval;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(logSeverity);
        dest.writeString(logEventCode);
        dest.writeString(logMessage);
        dest.writeFloat(logInterval);
        dest.writeLong(timestamp);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Severity: " + logSeverity);
        builder.append("\n");
        builder.append("LogEvent code: " + logEventCode);
        builder.append("\n");
        builder.append("Message: " + logMessage);
        builder.append("\n");
        builder.append("Interval: " + logInterval);
        builder.append("\n");
        builder.append("Timestamp: " + timestamp);
        builder.append("\n");
        return builder.toString();
    }
}
