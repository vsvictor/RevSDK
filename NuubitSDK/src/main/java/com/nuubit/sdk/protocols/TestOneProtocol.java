package com.nuubit.sdk.protocols;

/**
 * Created by victor on 12.04.17.
 */

public class TestOneProtocol {
    private EnumProtocol protocol;
    private long startTime;
    private long time;
    private long timeEnded;
    private String reason;

    public TestOneProtocol(EnumProtocol protocol, long time, long timeEnded, String reason) {
        this.protocol = protocol;
        this.time = time;
        this.timeEnded = timeEnded;
        this.reason = reason;
    }

    public TestOneProtocol(EnumProtocol protocol) {
        this(protocol, -1, -1, "Protocol not implemented");
    }

    public EnumProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(EnumProtocol protocol) {
        this.protocol = protocol;
    }

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    public long getStartTime(){
        return this.startTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimeEnded() {
        return timeEnded;
    }

    public void setTimeEnded(long timeEnded) {
        this.timeEnded = timeEnded;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Start request: "+getStartTime());
        builder.append(" Finish request: "+getTimeEnded());
        builder.append(" Result: "+getTime());
        builder.append(" Reason: "+getReason());
        return builder.toString();
    }

}
