package com.rev.sdk.protocols;

import android.support.annotation.NonNull;

/**
 * Created by victor on 05.04.17.
 */

public abstract class Protocol implements OnFuncProtocol {
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
}
