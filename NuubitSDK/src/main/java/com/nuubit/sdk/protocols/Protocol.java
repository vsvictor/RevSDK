package com.nuubit.sdk.protocols;

import android.support.annotation.NonNull;

import com.nuubit.sdk.NuubitConstants;

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

public abstract class Protocol implements OnFuncProtocol {
    protected static int errorCounter;
    protected EnumProtocol descroption;

    public Protocol() {
    }

    public static Protocol fromString(@NonNull String proto) {
        if (proto.equalsIgnoreCase("rmp")) return new RMPProtocol();
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
        return errorCounter >= NuubitConstants.ERRORS_IN_ROW;
    }

    public void zeroing() {
        errorCounter = 0;
    }
}
