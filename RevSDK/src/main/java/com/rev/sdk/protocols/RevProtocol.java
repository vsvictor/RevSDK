package com.rev.sdk.protocols;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by victor on 05.04.17.
 */

public class RevProtocol extends Protocol {

    public RevProtocol() {
        this.descroption = EnumProtocol.REV;
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
