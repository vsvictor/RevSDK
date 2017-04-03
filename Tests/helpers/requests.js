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
    configDefaultValues = require("../config/default").values;

exports.putConfig = function(appId, portalAPIKey, accountId, statsReportingIntervalSeconds) {
    request({
        url: configDefaultValues.urlAppsAPI + appId + configDefaultValues.appsOptionPublish,
        method: "PUT",
        json: true,
        headers: {
            "Accept": "application/json",
            "Authorization": portalAPIKey
        },
        body: {
            "app_name": "Rev Android SDK QA",
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
        console.log(body);
    });
};

