package com.rev.sdk.statistic.serialize;

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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rev.sdk.statistic.sections.Network;

import java.lang.reflect.Type;

public class NetworkSerialize implements JsonSerializer<Network> {
    @Override
    public JsonElement serialize(Network src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("cellular_ip_external", src.getCellularIPExternal());
        result.addProperty("cellular_ip_internal", src.getCellularIPInternal());
        result.addProperty("dns1", src.getDNS1());
        result.addProperty("dns2", src.getDNS2());
        result.addProperty("ip_reassemblies", src.getIpReassemblies());
        result.addProperty("ip_total_bytes_in", src.getIpTotalBytesIn());
        result.addProperty("ip_total_bytes_out", src.getIpTotalBytesOut());
        result.addProperty("ip_total_packets_in", src.getIpTotalPacketsIn());
        result.addProperty("ip_total_packets_out", src.getIpTotalPacketsOut());
        result.addProperty("rtt", src.getRtt());
        result.addProperty("tcp_bytes_in", src.getTcpBytesIn());
        result.addProperty("tcp_bytes_out", src.getCellularIPExternal());
        result.addProperty("tcp_retransmits", src.getCellularIPExternal());
        result.addProperty("transport_protocol", src.getCellularIPExternal());
        result.addProperty("udp_bytes_in", src.getCellularIPExternal());
        result.addProperty("udp_bytes_out", src.getCellularIPExternal());
        result.addProperty("wifi_dhcp", src.getCellularIPExternal());
        result.addProperty("wifi_extip", src.getCellularIPExternal());
        result.addProperty("wifi_gw", src.getCellularIPExternal());
        result.addProperty("wifi_ip", src.getCellularIPExternal());
        result.addProperty("wifi_mask", src.getCellularIPExternal());

        return result;
    }
}
