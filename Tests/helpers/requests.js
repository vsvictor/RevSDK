"use strict";

var request = require("request");

exports.putConfig = function(appId, portalAPIKey, accountId, statsReportingIntervalSeconds) {
    request({
        url: "https://api.nuubit.net:443/v1/apps/" + appId + "?options=publish",
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

