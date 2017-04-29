package com.nuubit.sdk.config;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.protocols.ListProtocol;

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

public class ConfigParamenetrs {
    private String sdk_release_version;
    private String logging_level;
    private String configuration_api_url;
    private int configuration_refresh_interval_sec;
    private int configuration_request_timeout_sec;
    private int configuration_stale_timeout_sec;
    private String edge_host;
    private OperationMode operation_mode;
    private ListProtocol allowed_transport_protocols;
    private String initial_transport_protocol;
    private String transport_monitoring_url;
    private String stats_reporting_url;
    private int stats_reporting_interval_sec;
    private String stats_reporting_level;
    private int stats_reporting_max_requests_per_report;
    private ListString domains_provisioned_list;
    private ListString domains_white_list;
    private ListString domains_black_list;
    private ListString internal_domains_black_list;
    private int a_b_testing_origin_offload_ratio;
    private int edge_connect_timeout_sec = 10;
    private int edge_data_receive_timeout_sec = 60;
    private int edge_first_byte_timeout_sec = 60;
    private String edge_sdk_domain = "revsdk.net";
    private int edge_quic_udp_port = 443;
    private int edge_failures_monitoring_interval_sec = 120;
    private int edge_failures_failover_threshold_percent = 80;

    public ConfigParamenetrs() {
        sdk_release_version = NuubitConstants.SDK_VERSION;
        logging_level = NuubitConstants.LEVEL;
        configuration_api_url = NuubitConstants.DEFAULT_CONFIG_URL;
        configuration_refresh_interval_sec = NuubitConstants.DEFAULT_CONFIG_INTERVAL;
        configuration_request_timeout_sec = NuubitConstants.DEFAULT_TIMEOUT_SEC;
        configuration_stale_timeout_sec = NuubitConstants.DEFAULT_STALE_INTERVAL;
        edge_host = NuubitConstants.DEFAULT_EDGE_HOST;
        operation_mode = OperationMode.off;
        allowed_transport_protocols = new ListProtocol();
        allowed_transport_protocols.add(EnumProtocol.STANDARD);
        initial_transport_protocol = EnumProtocol.STANDARD.toString();
        transport_monitoring_url = NuubitConstants.DEFAULT_TRANSPORT_MONITORING_URL;
        stats_reporting_url = NuubitConstants.DEFAULT_STATS_REPORTING_URL;
        stats_reporting_interval_sec = NuubitConstants.DEFAULT_STATS_REPORTING_INTERVAL;
        stats_reporting_level = NuubitConstants.LEVEL;
        stats_reporting_max_requests_per_report = NuubitConstants.DEFAULT_MAX_REQUEST_PER_REPORT;
        domains_provisioned_list = new ListString();
        domains_white_list = new ListString();
        domains_black_list = new ListString();
        internal_domains_black_list = new ListString();
        a_b_testing_origin_offload_ratio = 0;
        edge_connect_timeout_sec = 10;
        edge_data_receive_timeout_sec = 60;
        edge_first_byte_timeout_sec = 60;
        edge_sdk_domain = "revsdk.net";
        edge_quic_udp_port = 443;
        edge_failures_monitoring_interval_sec = 120;
        edge_failures_failover_threshold_percent = 80;
    }

    public String getSdkReleaseVersion() {
        return sdk_release_version;
    }

    public void setSdkReleaseVersion(String sdkReleaseVersion) {
        this.sdk_release_version = sdkReleaseVersion;
    }

    public String getLoggingLevel() {
        return logging_level;
    }

    public void setLoggingLevel(String loggingLevel) {
        this.logging_level = loggingLevel;
    }

    public String getConfigurationApiUrl() {
        return configuration_api_url;
    }

    public void setConfigurationApiUrl(String configurationApiUrl) {
        this.configuration_api_url = configurationApiUrl;
    }

    public int getConfigurationRefreshIntervalSec() {
        return configuration_refresh_interval_sec;
    }

    public void setConfigurationRefreshIntervalSec(int configurationRefreshIntervalSec) {
        this.configuration_refresh_interval_sec = configurationRefreshIntervalSec;
    }

    public int getConfigurationRequestTimeoutSec() {
        return configuration_request_timeout_sec;
    }

    public void setConfigurationRequestTimeoutSec(int configurationRequestTimeoutSec) {
        this.configuration_request_timeout_sec = configurationRequestTimeoutSec;
    }

    public int getConfigurationStaleTimeoutSec() {
        return configuration_stale_timeout_sec;
    }

    public void setConfigurationStaleTimeoutSec(int configurationStaleTimeoutSec) {
        this.configuration_stale_timeout_sec = configurationStaleTimeoutSec;
    }

    public String getEdgeHost() {
        return edge_host;
    }

    public void setEdgeHost(String edgeHost) {
        this.edge_host = edgeHost;
    }

    public OperationMode getOperationMode() {
        return operation_mode;
    }

    public void setOperationMode(OperationMode operationMode) {
        this.operation_mode = operationMode;
    }
    public ListProtocol getAllowedTransportProtocols() {
        return allowed_transport_protocols;
    }

    public void setAllowedTransportProtocols(ListProtocol allowedTransportProtocols) {
        this.allowed_transport_protocols = allowedTransportProtocols;
    }

    public String getInitialTransportProtocol() {
        return initial_transport_protocol;
    }

    public void setInitialTransportProtocol(String initialTransportProtocol) {
        this.initial_transport_protocol = initialTransportProtocol;
    }

    public String getTransportMonitoringUrl() {
        return transport_monitoring_url;
    }

    public void setTransportMonitoringUrl(String transportMonitoringUrl) {
        this.transport_monitoring_url = transportMonitoringUrl;
    }

    public String getStatsReportingUrl() {
        return stats_reporting_url;
    }

    public void setStatsReportingUrl(String statsReportingUrl) {
        this.stats_reporting_url = statsReportingUrl;
    }

    public int getStatsReportingIntervalSec() {
        return stats_reporting_interval_sec;
    }

    public void setStatsReportingIntervalSec(int statsReportingIntervalSec) {
        this.stats_reporting_interval_sec = statsReportingIntervalSec;
    }

    public String getStatsReportingLevel() {
        return stats_reporting_level;
    }

    public void setStatsReportingLevel(String statsReportingLevel) {
        this.stats_reporting_level = statsReportingLevel;
    }

    public int getStatsReportingMaxRequestsPerReport() {
        return stats_reporting_max_requests_per_report;
    }

    public void setStatsReportingMaxRequestsPerReport(int statsReportingMaxRequestsPerReport) {
        this.stats_reporting_max_requests_per_report = statsReportingMaxRequestsPerReport;
    }

    public ListString getDomainsProvisionedList() {
        return domains_provisioned_list;
    }

    public void setDomainsProvisionedList(ListString domainsProvisionedList) {
        this.domains_provisioned_list = domainsProvisionedList;
    }

    public ListString getDomainsWhiteList() {
        return domains_white_list;
    }

    public void setDomainsWhiteList(ListString domainsWhiteList) {
        this.domains_white_list = domainsWhiteList;
    }

    public ListString getDomainsBlackList() {
        return domains_black_list;
    }

    public void setDomainsBlackList(ListString domainsBlackList) {
        this.domains_black_list = domainsBlackList;
    }

    public ListString getInternalDomainsBlackList() {
        return internal_domains_black_list;
    }

    public void setInternalDomainsBlackList(ListString internalDomainsBlackList) {
        this.internal_domains_black_list = internalDomainsBlackList;
    }

    public int getABTestingOriginOffloadRatio() {
        return a_b_testing_origin_offload_ratio;
    }

    public void setABTestingOriginOffloadRatio(int abTestingOriginOffloadRatio) {
        this.a_b_testing_origin_offload_ratio = abTestingOriginOffloadRatio;
    }

    public int getEdgeConnectTimeoutSec() {
        return edge_connect_timeout_sec;
    }

    public void setEdgeConnectTimeoutSec(int edgeConnectTimeoutSec) {
        this.edge_connect_timeout_sec = edgeConnectTimeoutSec;
    }

    public int getEdgeDataReceiveTimeoutSec() {
        return edge_data_receive_timeout_sec;
    }

    public void setEdgeDataReceiveTimeoutSec(int edgeDataReceiveTimeoutSec) {
        this.edge_data_receive_timeout_sec = edgeDataReceiveTimeoutSec;
    }

    public int getEdgeFirstByteTimeoutSec() {
        return edge_first_byte_timeout_sec;
    }

    public void setEdgeFirstByteTimeoutSec(int edgeFirstByteTimeoutSec) {
        this.edge_first_byte_timeout_sec = edgeFirstByteTimeoutSec;
    }

    public String getEdgeSdkDomain() {
        return edge_sdk_domain;
    }

    public void setEdgeSdkDomain(String edgeSdkDomain) {
        this.edge_sdk_domain = edgeSdkDomain;
    }

    public int getEdgeQuicUdpPort() {
        return edge_quic_udp_port;
    }

    public void setEdgeQuicUdpPort(int edgeQuicUdpPort) {
        this.edge_quic_udp_port = edgeQuicUdpPort;
    }

    public int getEdgeFailuresMonitoringIntervalSec() {
        return edge_failures_monitoring_interval_sec;
    }

    public void setEdgeFailuresMonitoringIntervalSec(int edgeFailuresMonitoringIntervalSec) {
        this.edge_failures_monitoring_interval_sec = edgeFailuresMonitoringIntervalSec;
    }

    public int getEdgeFailuresFailoverThresholdPercent() {
        return edge_failures_failover_threshold_percent;
    }

    public void setEdgeFailuresFailoverThresholdPercent(int edgeFailuresFailoverThresholdPercent) {
        this.edge_failures_failover_threshold_percent = edgeFailuresFailoverThresholdPercent;
    }
}
