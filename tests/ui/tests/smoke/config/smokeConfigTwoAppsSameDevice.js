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
    actions = require("./../../../helpers/actions"),
    serverConfigs = require('./../../../helpers/appium-servers'),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    request = require("./../../../helpers/requests");

//TODO: test isn't implemented yet
describe("Smoke configuration load on two apps =>", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driverRevTester = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds82 = config.get('statsReportingIntervalSeconds82');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var defaultConfigValues = config.get('defaultConfigValues');

    before(function () {
        var serverConfig = serverConfigs.local;
        driverRevTester = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevTester);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .quit();
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driverRevTester
            .quit();
    });


    it("should check that two apps with SAME SDK keys "+
        "can work properly on the same device", function () {
        var serverConfig = serverConfigs.local;
        driverRevTester = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevTester);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .elementByClassName(App.menuBtn.button)
            .click()
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[2].text().should.become(defaultConfigValues.stats_reporting_interval_sec);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[4].text().should.become(defaultConfigValues.stats_reporting_level);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[6].text().should.become(defaultConfigValues.edge_failures_failover_threshold_percent);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[8].text().should.become(defaultConfigValues.edge_quic_udp_port);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[10].text().should.become(defaultConfigValues.edge_data_receive_timeout_sec);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[12].text().should.become(defaultConfigValues.app_name);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[14].text().should.become(defaultConfigValues.internal_domains_black_list);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[16].text().should.become(defaultConfigValues.a_b_testing_origin_offload_ratio);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[18].text().should.become(defaultConfigValues.sdk_release_version);
            })
            .elementsByXPath(Config.list.config)
            .then(function (els) {
                return els[20].text().should.become(defaultConfigValues.transport_monitoring_url);
            });
    });
});

