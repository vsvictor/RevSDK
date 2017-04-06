package com.rev.sdk.protocols;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by victor on 05.04.17.
 */

public interface OnFuncProtocol {
    Response send(Interceptor.Chain chain) throws IOException;

    long test(String url);
}
