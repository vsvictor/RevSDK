/*************************************************************************
 *
 * REV SOFTWARE CONFIDENTIAL
 *
 * [2013] - [2017] Rev Software, Inc.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Rev Software, Inc. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Rev Software, Inc.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Rev Software, Inc.
 */

"use strict";

var request = require("request"),
    config = require("config");
var urlAppsAPI = config.get('urlAppsAPI'),
    appsOptionPublish = config.get('appsOptionPublish'),
    urlGetStatsAPI = config.get('urlGetStatsAPI');
var responseBodyFromStatsOfAppDuringInterval = undefined;
var responseBodyFromStatsAggFlowWithStatusCode = undefined;

//Default configuration with not default stats_reporting_interval
exports.putConfig = function(appId, portalAPIKey, accountId, statsReportingIntervalSeconds) {
    request({
        url: urlAppsAPI + appId + appsOptionPublish,
        method: "PUT",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
        body: {
            "app_name": "Rev Demo Android SDK QA",
            "account_id": accountId,
            "configs": [
                {
                    "sdk_release_version": 0,
                    "logging_level": "debug",
                    "configuration_refresh_interval_sec": 60,
                    "configuration_stale_timeout_sec": 36600,
                    "operation_mode": "transfer_and_report",
                    "allowed_transport_protocols": [
                        "rmp",
                        "quic",
                        "standard"
                    ],
                    "initial_transport_protocol": "standard",
                    "stats_reporting_interval_sec": statsReportingIntervalSeconds,
                    "stats_reporting_level": "debug",
                    "stats_reporting_max_requests_per_report": 500,
                    "domains_provisioned_list": [],
                    "domains_white_list": [],
                    "domains_black_list": [],
                    "a_b_testing_origin_offload_ratio": 0
                }
            ],
            "comment":"dsfs"
        }
    }, function (error, response, body){
        //console.log(body);
    });
};

//Default configuration with not default domain lists and stats reporting interval
exports.putConfigWithDomainsLists = function(appId, portalAPIKey, accountId, statsReportingIntervalSeconds,
                                                 domainsWhiteList, domainBlackList, domainsProvisionedList) {
    request({
        url: urlAppsAPI + appId + appsOptionPublish,
        method: "PUT",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
        body: {
            "app_name": "NuubitTester",
            "account_id": accountId,
            "configs": [
                {
                    "sdk_release_version": 0,
                    "logging_level": "debug",
                    "configuration_refresh_interval_sec": 60,
                    "configuration_stale_timeout_sec": 36600,
                    "operation_mode": "transfer_and_report",
                    "allowed_transport_protocols": [
                        "rmp",
                        "quic",
                        "standard"
                    ],
                    "initial_transport_protocol": "standard",
                    "stats_reporting_interval_sec": statsReportingIntervalSeconds,
                    "stats_reporting_level": "debug",
                    "stats_reporting_max_requests_per_report": 500,
                    "domains_provisioned_list": domainsProvisionedList,
                    "domains_white_list": domainsWhiteList,
                    "domains_black_list": domainBlackList,
                    "a_b_testing_origin_offload_ratio": 0
                }
            ],
            "comment":"dsfs"
        }
    }, function (error, response, body){
        //console.log('ERROR ===>>> ', error);
        //console.log('BODY ===>>> ', body);
        //console.log('RESPONSE ===>>> ', response);
        //console.log('======================================================================================');
    });
};

//Default configuration with not default domain lists and stats reporting interval
exports.putConfigWithAllowedProtocols = function(appId, portalAPIKey, accountId, allowedProtocols) {
    request({
        url: urlAppsAPI + appId + appsOptionPublish,
        method: "PUT",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
        body: {
            "app_name": "Rev Tester Android SDK QA",
            "account_id": accountId,
            "configs": [
                {
                    "sdk_release_version": 0,
                    "logging_level": "debug",
                    "configuration_refresh_interval_sec": 60,
                    "configuration_stale_timeout_sec": 36600,
                    "operation_mode": "transfer_and_report",
                    "allowed_transport_protocols": allowedProtocols,
                    "initial_transport_protocol": "standard",
                    "stats_reporting_interval_sec": 60,
                    "stats_reporting_level": "debug",
                    "stats_reporting_max_requests_per_report": 500,
                    "domains_provisioned_list": [],
                    "domains_white_list": [],
                    "domains_black_list": [],
                    "a_b_testing_origin_offload_ratio": 0
                }
            ],
            "comment":"dsfs"
        }
    }, function (error, response, body){
              //console.log(body);
    });
};

exports.putConfigWithConfigStaleTimeoutSec = function(appId, portalAPIKey, accountId, configStaleTimeoutSec) {
    request({
        url: urlAppsAPI + appId + appsOptionPublish,
        method: "PUT",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
        body: {
            "app_name": "Rev Demo Android SDK QA",
            "account_id": accountId,
            "configs": [
                {
                    "sdk_release_version": 0,
                    "logging_level": "debug",
                    "configuration_refresh_interval_sec": 60,
                    "configuration_stale_timeout_sec": configStaleTimeoutSec,
                    "operation_mode": "transfer_and_report",
                    "allowed_transport_protocols": [
                        "rmp",
                        "quic",
                        "standard"
                    ],
                    "initial_transport_protocol": "standard",
                    "stats_reporting_interval_sec": 60,
                    "stats_reporting_level": "debug",
                    "stats_reporting_max_requests_per_report": 500,
                    "domains_provisioned_list": [],
                    "domains_white_list": [],
                    "domains_black_list": [],
                    "a_b_testing_origin_offload_ratio": 0
                }
            ],
            "comment":"dsfs"
        }
    }, function (error, response, body){
        //console.log(body);
    });
};

exports.getStatsOfAppDuringInterval = function(appId, portalAPIKey, fromTimeStamp, toTimeStamp) {
    request({
        url: urlGetStatsAPI + "app/" + appId + "?from_timestamp=" + fromTimeStamp + "&to_timestamp=" + toTimeStamp + "&",
        method: "GET",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
    }, function (error, response, body){
        //console.log(body);
        responseBodyFromStatsOfAppDuringInterval = body;
    });
};

exports.getResponseBodyFromStatsOfAppDuringInterval = function() {
    return responseBodyFromStatsOfAppDuringInterval;
};

exports.getStatsAggFlowWithStatusCode = function(appId, portalAPIKey, fromTimeStamp, toTimeStamp) {
    https://api.nuubit.net:443/v1/stats/sdk/agg_flow?app_id=58d535b8519150d22ab102b4&from_timestamp=1507006844978&to_timestamp=1507008273065&report_type=status_code&&&&&

    request({
        url: urlGetStatsAPI + "agg_flow?app_id=" + appId + "&from_timestamp=" + fromTimeStamp + "&to_timestamp=" + toTimeStamp + "&report_type=status_code&&&&&",
        method: "GET",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
    }, function (error, response, body){
        //console.log(body);
        responseBodyFromStatsAggFlowWithStatusCode = body;
    });
};

exports.getResponseBodyFromStatsAggFlowWithStatusCode = function() {
    return responseBodyFromStatsAggFlowWithStatusCode;
};


