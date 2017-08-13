package com.nuubit.sdk.protocols;

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

public enum EnumProtocol {
    STANDARD,
    QUIC,
    RMP,
    HTTP,
    HTTPS,
    ALL,
    UNDEFINED;

    @Override
    public String toString() {
        switch (this) {
            case STANDARD:
                return "standard";
            case QUIC:
                return "quic";
            case RMP:
                return "rmp";
            case HTTP:
                return "http";
            case HTTPS:
                return "https";
            case ALL:
                return "all";
            default:
                return "undefined";
        }
    }

    public static EnumProtocol fromString(String name) {
        switch (name.toLowerCase()) {
            case "standard":
                return STANDARD;
            case "quic":
                return QUIC;
            case "rmp":
                return RMP;
            case "http":
                return HTTP;
            case "https":
                return HTTPS;
            case "all":
                return ALL;
            default:
                return UNDEFINED;
        }
    }

    public static Protocol createInstance(EnumProtocol inst) {
        switch (inst) {
            case STANDARD:
                return new StandardProtocol();
/*            case QUIC:
                return new QUICProtocol();*/
            case RMP:
                return new RMPProtocol();
        }
        return null;
    }
}

