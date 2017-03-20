package com.rev.sdk.protocols;

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

public enum Protocol {
    STANDART,
    QUIC,
    REV,
    HTTP,
    HTTPS,
    UNDEFINED;

    @Override
    public String toString() {
        switch (this){
            case STANDART: return "standard";
            case QUIC: return "quic";
            case REV: return "rmp";
            case HTTP: return "http";
            case HTTPS: return "https";
            default:
                return "UNDEFINED";
        }
    }
    public static Protocol fromString(String name) {
        switch (name.toLowerCase()){
            case "standard": return STANDART;
            case "quic": return QUIC;
            case "rmp": return REV;
            case "http": return HTTP;
            case "https": return HTTPS;
            default: return UNDEFINED;
        }
    }
}

