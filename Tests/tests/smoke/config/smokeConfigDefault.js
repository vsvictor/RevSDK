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

require("./../../../helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    config = require('config'),
    serverConfigs = require('./../../../helpers/appium-servers'),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    Settings = require("./../../../page_objects/RevTester/settings"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('toggleNetwork', Settings.toggleNetwork);
wd.addPromiseChainMethod('getConfigurationPage', App.getConfigurationPage);
wd.addPromiseChainMethod('getConfigurationList', App.getConfigurationList);
wd.addPromiseChainMethod('getConfigValues', App.getConfigValues);

var driver = undefined;
var serverConfig = serverConfigs.local;
driver = wd.promiseChainRemote(serverConfig);
logging.configure(driver);
var desired = _.clone(caps.android19);
desired.app = apps.androidTester;

describe("Smoke: default configuration", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);

    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds82 = config.get('statsReportingIntervalSeconds82');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var defaultConfigValues = config.get('defaultConfigValues');

    before(function () {
        //Run Rev Tester first time, turn on off the network, quit RevTester
        return driver
            .init(desired)
            .toggleNetwork(driver)
            .quit();
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        //turn on the network and quit RevTester
        return driver
            .toggleNetwork(driver)
            .quit();
    });


    it("should have a default config on the first initialization and cannot load config from API", function () {
        //Run Nuubittester second time
        //check that it has default config
        var configValues = [];
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .getConfigurationPage(driver)
            .getConfigurationList(driver)
            .getConfigValues(driver)
            .then(function (configListValues) {
                for (var i = 0; i < configListValues.length; i++) {
                    configValues[i] = configListValues[i].text();
                };
                return configValues;
            })
            .then(function () {
                return configValues[0].should.become(defaultConfigValues.stats_reporting_interval_sec);
            })
            .then(function () {
                return configValues[1].should.become(defaultConfigValues.stats_reporting_level);
            })
            .then(function () {
                return configValues[2].should.become(defaultConfigValues.edge_failures_failover_threshold_percent);
            })
            .then(function () {
                return configValues[3].should.become(defaultConfigValues.edge_quic_udp_port);
            })
            .then(function () {
                return configValues[4].should.become(defaultConfigValues.edge_data_receive_timeout_sec);
            })
            .then(function () {
                return configValues[5].should.become(defaultConfigValues.app_name);
            })
            .then(function () {
                return configValues[6].should.become(defaultConfigValues.internal_domains_black_list);
            })
            .then(function () {
                return configValues[7].should.become(defaultConfigValues.a_b_testing_origin_offload_ratio);
            })
            .then(function () {
                return configValues[8].should.become(defaultConfigValues.sdk_release_version);
            })
            .then(function () {
                return configValues[9].should.become(defaultConfigValues.transport_monitoring_url);
            });
    });
});

