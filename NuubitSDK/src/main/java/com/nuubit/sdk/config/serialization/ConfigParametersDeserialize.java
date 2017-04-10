package com.nuubit.sdk.config.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuubit.sdk.config.ConfigParamenetrs;
import com.nuubit.sdk.config.ListString;
import com.nuubit.sdk.config.OperationMode;

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
    private static final String TAG = ConfigParametersDeserialize.class.getSimpleName();
    @Override
    public ConfigParamenetrs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ConfigParamenetrs config = new ConfigParamenetrs();
        OperationModeDeserialize opDeser = new OperationModeDeserialize();
        ListStringDeserializer lsDeser = new ListStringDeserializer();
        TransportProtocolDeserialize trDeser = new TransportProtocolDeserialize();
        //Log.i(TAG,"-3d");
        if(json == null) return config;
        //Log.i(TAG,"-2d");
        JsonObject obj = json.getAsJsonObject();
        //Log.i(TAG,"-1d");
        if(obj == null) return config;
        //Log.i(TAG,"0d");
        config.setSdkReleaseVersion(obj.get("sdk_release_version").getAsString());
        //Log.i(TAG,"1d");
        config.setLoggingLevel(obj.get("logging_level").getAsString());
        //Log.i(TAG,"2d");
        config.setConfigurationApiUrl(obj.get("configuration_api_url").getAsString());
        //Log.i(TAG,"3d");
        config.setConfigurationRefreshIntervalSec(obj.get("configuration_refresh_interval_sec").getAsInt());
        //Log.i(TAG,"4d");
        config.setConfigurationRequestTimeoutSec(obj.get("configuration_request_timeout_sec").getAsInt());
        //Log.i(TAG,"5d");
        config.setConfigurationStaleTimeoutSec(obj.get("configuration_stale_timeout_sec").getAsInt());
        //Log.i(TAG,"6d");
        config.setEdgeHost(obj.get("edge_host").getAsString());
        //Log.i(TAG,"7d");
        config.setOperationMode(opDeser.deserialize(obj.get("operation_mode"), OperationMode.class, context));
        //Log.i(TAG,"8d");
        config.setAllowedTransportProtocols(trDeser.deserialize(obj.get("allowed_transport_protocols").getAsJsonArray(), ListString.class, context));
        //Log.i(TAG,"9d");
        config.setInitialTransportProtocol(obj.get("initial_transport_protocol").getAsString());
        //Log.i(TAG,"10d");
        config.setTransportMonitoringUrl(obj.get("transport_monitoring_url").getAsString());
        //Log.i(TAG,"11d");
        config.setStatsReportingUrl(obj.get("stats_reporting_url").getAsString());
        //Log.i(TAG,"12d");
        config.setStatsReportingIntervalSec(obj.get("stats_reporting_interval_sec").getAsInt());
        //Log.i(TAG,"13d");
        config.setStatsReportingLevel(obj.get("stats_reporting_level").getAsString());
        //Log.i(TAG,"14d");
        config.setStatsReportingMaxRequestsPerReport(obj.get("stats_reporting_max_requests_per_report").getAsInt());
        //Log.i(TAG,"15d");
        config.setDomainsProvisionedList(lsDeser.deserialize(obj.get("domains_provisioned_list").getAsJsonArray(), ListString.class, context));
        //Log.i(TAG,"16d");
        config.setDomainsWhiteList(lsDeser.deserialize(obj.get("domains_white_list").getAsJsonArray(), ListString.class, context));
        //Log.i(TAG,"17d");
        config.setDomainsBlackList(lsDeser.deserialize(obj.get("domains_black_list").getAsJsonArray(), ListString.class, context));
        //Log.i(TAG,"18d");
        config.setInternalDomainsBlackList(lsDeser.deserialize(obj.get("internal_domains_black_list").getAsJsonArray(), ListString.class, context));
        //Log.i(TAG,"19d");
        config.setABTestingOriginOffloadRatio(obj.get("a_b_testing_origin_offload_ratio").getAsInt());
        //Log.i(TAG,"20d");
        config.setEdgeConnectTimeoutSec(obj.get("edge_connect_timeout_sec").getAsInt());
        //Log.i(TAG,"21d");
        config.setEdgeDataReceiveTimeoutSec(obj.get("edge_data_receive_timeout_sec").getAsInt());
        //Log.i(TAG,"22d");
        config.setEdgeFirstByteTimeoutSec(obj.get("edge_first_byte_timeout_sec").getAsInt());
        //Log.i(TAG,"23d");
        config.setEdgeSdkDomain(obj.get("edge_sdk_domain").getAsString());
        //Log.i(TAG,"24d");
        config.setEdgeQuicUdpPort(obj.get("edge_quic_udp_port").getAsInt());
        //Log.i(TAG,"25d");
        config.setEdgeFailuresMonitoringIntervalSec(obj.get("edge_failures_monitoring_interval_sec").getAsInt());
        //Log.i(TAG,"26d");
        config.setEdgeFailuresFailoverThresholdPercent(obj.get("edge_failures_failover_threshold_percent").getAsInt());
        //Log.i(TAG,"27d");
        return config;
    }
}
