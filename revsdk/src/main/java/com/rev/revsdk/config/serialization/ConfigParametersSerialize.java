package com.rev.revsdk.config.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
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

public class ConfigParametersSerialize implements JsonSerializer<ConfigParamenetrs> {
    @Override
    public JsonElement serialize(ConfigParamenetrs src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        OperationModeSerialize serMode = new OperationModeSerialize();
        ListStrintgSerialize serArr = new ListStrintgSerialize();
        TransportProtocolSerialize serTransport = new TransportProtocolSerialize();

        result.addProperty("sdk_release_version", src.getSdkReleaseVersion());
        result.addProperty("logging_level", src.getLoggingLevel());
        result.addProperty("configuration_api_url", src.getConfigurationApiUrl());
        result.addProperty("configuration_refresh_interval_sec", src.getConfigurationRefreshIntervalSec());
        result.addProperty("configuration_request_timeout_sec", src.getConfigurationRequestTimeoutSec());
        result.addProperty("configuration_stale_timeout_sec", src.getConfigurationStaleTimeoutSec());
        result.addProperty("edge_host", src.getEdgeHost());
        result.add("operation_mode", serMode.serialize(src.getOperationMode(),OperationMode.class, context));
        result.add("allowed_transport_protocols", serTransport.serialize(src.getAllowedTransportProtocols(), ListString.class, context));
        result.addProperty("initial_transport_protocol", src.getInitialTransportProtocol());
        result.addProperty("transport_monitoring_url", src.getTransportMonitoringUrl());
        result.addProperty("stats_reporting_url", src.getStatsReportingUrl());
        result.addProperty("stats_reporting_interval_sec", src.getStatsReportingIntervalSec());
        result.addProperty("stats_reporting_level", src.getStatsReportingLevel());
        result.addProperty("stats_reporting_max_requests_per_report", src.getStatsReportingMaxRequestsPerReport());
        result.add("domains_provisioned_list", serArr.serialize(src.getDomainsProvisionedList(), ListString.class, context));
        result.add("domains_white_list", serArr.serialize(src.getDomainsWhiteList(), ListString.class, context));
        result.add("domains_black_list", serArr.serialize(src.getDomainsBlackList(), ListString.class, context));
        result.add("internal_domains_black_list", serArr.serialize(src.getInternalDomainsBlackList(), ListString.class, context));
        result.addProperty("a_b_testing_origin_offload_ratio", src.getABTestingOriginOffloadRatio());
        result.addProperty("edge_connect_timeout_sec", src.getEdgeConnectTimeoutSec());
        result.addProperty("edge_data_receive_timeout_sec", src.getEdgeDataReceiveTimeoutSec());
        result.addProperty("edge_first_byte_timeout_sec", src.getEdgeFirstByteTimeoutSec());
        result.addProperty("edge_sdk_domain", src.getEdgeSdkDomain());
        result.addProperty("edge_quic_udp_port", src.getEdgeQuicUdpPort());
        result.addProperty("edge_failures_monitoring_interval_sec", src.getEdgeFailuresMonitoringIntervalSec());
        result.addProperty("edge_failures_failover_threshold_percent", src.getEdgeFailuresFailoverThresholdPercent());
        return result;
    }
}
