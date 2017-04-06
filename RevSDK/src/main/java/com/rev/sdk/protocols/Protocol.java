package com.rev.sdk.protocols;

import android.support.annotation.NonNull;

import com.rev.sdk.Constants;

/**
 * Created by victor on 05.04.17.
 */

public abstract class Protocol implements OnFuncProtocol {
    protected static int errorCounter;
    protected EnumProtocol descroption;

    public Protocol() {
    }

    public static Protocol fromString(@NonNull String proto) {
        if (proto.equalsIgnoreCase("rmp")) return new RevProtocol();
        else if (proto.equalsIgnoreCase("quic")) return new QUICProtocol();
        else return new StandardProtocol();
    }

    public EnumProtocol getDescription() {
        return descroption;
    }

    public void errorIncrement() {
        this.errorCounter++;
    }

    public boolean isOverflow() {
        return errorCounter >= Constants.ERRORS_IN_ROW;
    }

    public void zeroing() {
        errorCounter = 0;
    }
}
