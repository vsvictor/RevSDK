package com.nuubit.sdk.config.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nuubit.sdk.config.ConfigParamenetrs;
import com.nuubit.sdk.config.ListString;

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
    private static final String TAG = ConfigParametersSerialize.class.getSimpleName();
    @Override
    public JsonElement serialize(ConfigParamenetrs src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        OperationModeSerialize serMode = new OperationModeSerialize();
        ListStrintgSerialize serArr = new ListStrintgSerialize();
        TransportProtocolSerialize serTransport = new TransportProtocolSerialize();
        //Log.i(TAG,"1s");
        result.addProperty("sdk_release_version", src.getSdkReleaseVersion());
        //Log.i(TAG,"2s");
        result.addProperty("logging_level", src.getLoggingLevel());
        //Log.i(TAG,"3s");
        result.addProperty("configuration_api_url", src.getConfigurationApiUrl());
        //Log.i(TAG,"4s");
        result.addProperty("configuration_refresh_interval_sec", src.getConfigurationRefreshIntervalSec());
        //Log.i(TAG,"5s");
        result.addProperty("configuration_request_timeout_sec", src.getConfigurationRequestTimeoutSec());
        //Log.i(TAG,"6s");
        result.addProperty("configuration_stale_timeout_sec", src.getConfigurationStaleTimeoutSec());
        //Log.i(TAG,"7s");
        result.addProperty("edge_host", src.getEdgeHost());
        //Log.i(TAG,"8s");
        result.addProperty("operation_mode", src.getOperationMode().toString());
        //Log.i(TAG,"9s");
        result.add("allowed_transport_protocols", serTransport.serialize(src.getAllowedTransportProtocols(), ListString.class, context));
        //Log.i(TAG,"10s");
        result.addProperty("initial_transport_protocol", src.getInitialTransportProtocol());
        //Log.i(TAG,"11s");
        result.addProperty("transport_monitoring_url", src.getTransportMonitoringUrl());
        //Log.i(TAG,"12s");
        result.addProperty("stats_reporting_url", src.getStatsReportingUrl());
        //Log.i(TAG,"13s");
        result.addProperty("stats_reporting_interval_sec", src.getStatsReportingIntervalSec());
        //Log.i(TAG,"14s");
        result.addProperty("stats_reporting_level", src.getStatsReportingLevel());
        //Log.i(TAG,"15s");
        result.addProperty("stats_reporting_max_requests_per_report", src.getStatsReportingMaxRequestsPerReport());
        //Log.i(TAG,"16s");
        result.add("domains_provisioned_list", serArr.serialize(src.getDomainsProvisionedList(), ListString.class, context));
        //Log.i(TAG,"17s");
        result.add("domains_white_list", serArr.serialize(src.getDomainsWhiteList(), ListString.class, context));
        //Log.i(TAG,"18s");
        result.add("domains_black_list", serArr.serialize(src.getDomainsBlackList(), ListString.class, context));
        //Log.i(TAG,"19s");
        result.add("internal_domains_black_list", serArr.serialize(src.getInternalDomainsBlackList(), ListString.class, context));
        //Log.i(TAG,"20s");
        result.addProperty("a_b_testing_origin_offload_ratio", src.getABTestingOriginOffloadRatio());
        //Log.i(TAG,"21s");
        result.addProperty("edge_connect_timeout_sec", src.getEdgeConnectTimeoutSec());
        //Log.i(TAG,"22s");
        result.addProperty("edge_data_receive_timeout_sec", src.getEdgeDataReceiveTimeoutSec());
        //Log.i(TAG,"23s");
        result.addProperty("edge_first_byte_timeout_sec", src.getEdgeFirstByteTimeoutSec());
        //Log.i(TAG,"24s");
        result.addProperty("edge_sdk_domain", src.getEdgeSdkDomain());
        //Log.i(TAG,"25s");
        result.addProperty("edge_quic_udp_port", src.getEdgeQuicUdpPort());
        //Log.i(TAG,"26s");
        result.addProperty("edge_failures_monitoring_interval_sec", src.getEdgeFailuresMonitoringIntervalSec());
        //Log.i(TAG,"27s");
        result.addProperty("edge_failures_failover_threshold_percent", src.getEdgeFailuresFailoverThresholdPercent());
        //Log.i(TAG,"28s");
        return result;
    }
}
