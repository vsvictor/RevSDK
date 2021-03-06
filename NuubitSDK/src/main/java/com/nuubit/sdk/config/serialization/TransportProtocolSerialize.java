package com.nuubit.sdk.config.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.protocols.ListProtocol;

import java.lang.reflect.Type;

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

public class TransportProtocolSerialize implements JsonSerializer<ListProtocol> {
    @Override
    public JsonElement serialize(ListProtocol src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray result = new JsonArray();
        for (EnumProtocol enumProtocol : src) {
            String s = null;
            switch (enumProtocol) {
                case  STANDARD: {s="standard";break;}
                case  QUIC: {s="quic";break;}
                case RMP: {
                    s = "rmp";
                    break;
                }
            }
            result.add(s);
        }
        return result;
    }
}
