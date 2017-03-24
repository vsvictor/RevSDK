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
}
