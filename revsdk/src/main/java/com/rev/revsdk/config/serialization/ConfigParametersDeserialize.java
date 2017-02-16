package com.rev.revsdk.config.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rev.revsdk.config.ConfigParamenetrs;
import com.rev.revsdk.config.ListString;
import com.rev.revsdk.config.OperationMode;

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

public class ConfigParametersDeserialize implements JsonDeserializer<ConfigParamenetrs> {
    @Override
    public ConfigParamenetrs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ConfigParamenetrs config = new ConfigParamenetrs();
        if(json == null) return config;

        OperationModeDeserialize opDeser = new OperationModeDeserialize();
        ListStringDeserializer lsDeser = new ListStringDeserializer();
        TransportProtocolDeserialize trDeser = new TransportProtocolDeserialize();
        JsonObject obj = /**Created by
 * Created by victor on 05.02.17.
 */
json.getAsJsonObject();
        if(obj == null) return config;

        config.setSdkReleaseVersion(obj.get("sdk_release_version").getAsString());
        config.setLoggingLevel(obj.get("logging_level").getAsString());
        config.setConfigurationApiUrl(obj.get("configuration_api_url").getAsString());
        config.setConfigurationRefreshIntervalSec(obj.get("configuration_refresh_interval_sec").getAsInt());
        config.setConfigurationRequestTimeoutSec(obj.get("configuration_request_timeout_sec").getAsInt());
        config.setConfigurationStaleTimeoutSec(obj.get("configuration_stale_timeout_sec").getAsInt());
        config.setEdgeHost(obj.get("edge_host").getAsString());
        config.setOperationMode(opDeser.deserialize(obj.get("operation_mode"), OperationMode.class, context));
        config.setAllowedTransportProtocols(trDeser.deserialize(obj.get("allowed_transport_protocols").getAsJsonArray(), ListString.class, context));
        config.setInitialTransportProtocol(obj.get("initial_transport_protocol").getAsString());
        config.setTransportMonitoringUrl(obj.get("transport_monitoring_url").getAsString());
        config.setStatsReportingUrl(obj.get("stats_reporting_url").getAsString());
        config.setStatsReportingIntervalSec(obj.get("stats_reporting_interval_sec").getAsInt());
        config.setStatsReportingLevel(obj.get("stats_reporting_level").getAsString());
        config.setStatsReportingMaxRequestsPerReport(obj.get("stats_reporting_max_requests_per_report").getAsInt());
        config.setDomainsProvisionedList(lsDeser.deserialize(obj.get("domains_provisioned_list").getAsJsonArray(), ListString.class, context));
        config.setDomainsWhiteList(lsDeser.deserialize(obj.get("domains_white_list").getAsJsonArray(), ListString.class, context));
        config.setDomainsBlackList(lsDeser.deserialize(obj.get("domains_black_list").getAsJsonArray(), ListString.class, context));
        config.setInternalDomainsBlackList(lsDeser.deserialize(obj.get("internal_domains_black_list").getAsJsonArray(), ListString.class, context));
        config.setABTestingOriginOffloadRatio(obj.get("a_b_testing_origin_offload_ratio").getAsInt());
        config.setEdgeConnectTimeoutSec(obj.get("edge_connect_timeout_sec").getAsInt());
        config.setEdgeDataReceiveTimeoutSec(obj.get("edge_data_receive_timeout_sec").getAsInt());
        config.setEdgeFirstByteTimeoutSec(obj.get("edge_first_byte_timeout_sec").getAsInt());
        config.setEdgeSdkDomain(obj.get("edge_sdk_domain").getAsString());
        config.setEdgeQuicUdpPort(obj.get("edge_quic_udp_port").getAsInt());
        config.setEdgeFailuresMonitoringIntervalSec(obj.get("edge_failures_monitoring_interval_sec").getAsInt());
        config.setEdgeFailuresFailoverThresholdPercent(obj.get("edge_failures_failover_threshold_percent").getAsInt());
        return config;
    }
}
