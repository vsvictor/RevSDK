package com.rev.racer.model;

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

public class Row {
    private String source;
    private long start;
    private long finish;
    private int codeResult;
    private long body;
    private long payload;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public int getCodeResult() {
        return codeResult;
    }

    public void setCodeResult(int codeResult) {
        this.codeResult = codeResult;
    }

    public long getBody() {
        return body;
    }

    public void setBody(long body) {
        this.body = body;
    }

    public long getPayload() {
        return payload;
    }

    public void setPayload(long payload) {
        this.payload = payload;
    }

    public long getTimeInMillis() {
        return this.getFinish() - this.getStart();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Source:");
        builder.append(" '" + this.getSource() + "'");
        builder.append(" Start: ");
        builder.append(this.getStart());
        builder.append(" Finish: ");
        builder.append(this.getFinish());
        builder.append(" Time:");
        builder.append(this.getTimeInMillis());
        builder.append(" Body: ");
        builder.append(this.getBody());
        builder.append(" Payload: ");
        builder.append(this.getPayload());
        return builder.toString();
    }

    public String toTable() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getTimeInMillis());
        builder.append(" (");
        builder.append(this.getCodeResult());
        builder.append(" , ");
        builder.append(this.getBody());
        builder.append("/");
        builder.append(this.getPayload());
        builder.append(")");
        return builder.toString();
    }
}
