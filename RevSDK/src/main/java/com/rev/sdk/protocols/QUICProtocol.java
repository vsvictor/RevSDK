package com.rev.sdk.protocols;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by victor on 05.04.17.
 */

public class QUICProtocol extends Protocol {

    public QUICProtocol() {
        this.descroption = EnumProtocol.QUIC;
    }
    @Override
    public Response send(Interceptor.Chain chain) {
        return null;
    }

    @Override
    public long test(String url) {
        return -1;
    }
}
