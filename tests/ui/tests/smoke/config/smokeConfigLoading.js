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
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('getConfigurationPage', App.getConfigurationPage);
wd.addPromiseChainMethod('getStatsReportingInterval', Config.getStatsReportingInterval);
wd.addPromiseChainMethod('getConfigurationList', Config.getConfigurationList);
wd.addPromiseChainMethod('getConfigVariables', Config.getConfigVariables);

describe("Smoke: configuration loading", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds82 = config.get('statsReportingIntervalSeconds82');
    var defaultConfigVars = config.get('defaultConfigVars');

    before(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds82);

        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("should load config on first initialization", function () {
        return driver
            .getConfigurationPage(driver)
            .getStatsReportingInterval(driver).then(function(element) {
                 return element.text().should.become(statsReportingIntervalSeconds82 + '');
            });
    });

    it("should load valid config parameters", function () {
        var configVariables = [];
        return driver
            .getConfigurationPage(driver)
            .getConfigurationList(driver)
            .getConfigVariables(driver)
            .then(function (configListVariables) {
                for (var i = 0; i < configListVariables.length; i++) {
                    configVariables[i] = configListVariables[i].text();
                };
                return configVariables;
            })
            .then(function () {
                return configVariables[0].should.become(defaultConfigVars.domains_black_list);
            })
            .then(function () {
                return configVariables[1].should.become(defaultConfigVars.a_b_testing_origin_offload_ratio);
            })
            .then(function () {
                return configVariables[2].should.become(defaultConfigVars.stats_reporting_interval_sec);
            })
            .then(function () {
                return configVariables[3].should.become(defaultConfigVars.stats_reporting_level);
            })
            .then(function () {
                return configVariables[4].should.become(defaultConfigVars.domains_provisioned_list);
            })
            .then(function () {
                return configVariables[5].should.become(defaultConfigVars.allowed_transport_protocols);
            })
            .then(function () {
                return configVariables[6].should.become(defaultConfigVars.configuration_stale_timeout_sec);
            })
            .then(function () {
                return configVariables[7].should.become(defaultConfigVars.edge_failures_monitoring_interval_sec);
            })
            .then(function () {
                return configVariables[8].should.become(defaultConfigVars.configuration_api_url);
            })
            .then(function () {
                return configVariables[9].should.become(defaultConfigVars.edge_quic_udp_port);
            });
    });
});

