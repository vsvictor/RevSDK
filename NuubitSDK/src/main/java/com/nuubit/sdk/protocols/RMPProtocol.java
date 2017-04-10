package com.nuubit.sdk.protocols;

import okhttp3.Interceptor;
import okhttp3.Response;

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

public class RMPProtocol extends Protocol {

    public RMPProtocol() {
        this.descroption = EnumProtocol.RMP;
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
