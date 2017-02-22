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

import com.rev.revsdk.Constants;

public class Event {
    private String logSeverity;
    private String logEventCode;
    private String logMessage;
    private float logInterval;
    private long timestamp;

    public Event(){
        this.logSeverity = logSeverity();
        this.logEventCode = logEventCode();
        this.logMessage = logMessage();
        this.logInterval = logInterval();
        this.timestamp = timestamp();
    }
    private String logSeverity(){
        return Constants.undefined;
    }
    private String logEventCode(){
        return Constants.undefined;
    }
    private String logMessage(){
        return Constants.undefined;
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
}
