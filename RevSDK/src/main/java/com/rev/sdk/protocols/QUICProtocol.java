package com.rev.sdk.protocols;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by victor on 05.04.17.
 */

public class QUICProtocol extends Protocol {
    @Override
    public Response send(Interceptor.Chain chain) {
        return null;
    }

    @Override
    public long test() {
        return 0;
    }
}
