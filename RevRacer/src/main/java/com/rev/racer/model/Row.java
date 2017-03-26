package com.rev.racer.model;

/**
 * Created by victor on 24.03.17.
 */

public class Row {
    private long start;
    private long finish;
    private long body;
    private long payload;

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
        builder.append("Start: ");
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
}
