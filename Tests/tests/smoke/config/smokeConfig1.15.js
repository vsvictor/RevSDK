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
    Settings = require("./../../../page_objects/RevTester/settingsWindow"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('openSettings', actions.openSettings);
wd.addPromiseChainMethod('closeSettings', actions.closeSettings);

var driverRevTester = undefined;
var serverConfig = serverConfigs.local;
driverRevTester = wd.promiseChainRemote(serverConfig);
logging.configure(driverRevTester);
var desired = _.clone(caps.android19);
desired.app = apps.androidTester;

describe("Smoke Configuration", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);

    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds82 = config.get('statsReportingIntervalSeconds82');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var defaultConfigValues = config.get('defaultConfig');

    before(function () {
        //Run Rev Tester, turn on off the network, quit RevTester
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .openSettings()
            .elementByXPath(Settings.button.networkImage)
            .click()
            .elementByClassName(Settings.checkBox.switchNetwork)
            .click()
            .closeSettings()
            .quit();
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        //turn on the network and quit RevTester
        return driverRevTester
            .openSettings()
            .elementByXPath(Settings.button.networkImage)
            .click()
            .elementByClassName(Settings.checkBox.switchNetwork)
            .click()
            .closeSettings()
            .quit();
    });


    it("should have a defauld config on the first initizlization and cannot load config from API", function () {
        //Run Nuubittester second time
        //check that it has default config
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .elementByClassName(App.menuBtn.button)
            .click()
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[2].text().should.become(defaultConfigValues.stats_reporting_interval_sec);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[4].text().should.become(defaultConfigValues.stats_reporting_level);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[6].text().should.become(defaultConfigValues.edge_failures_failover_threshold_percent);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[8].text().should.become(defaultConfigValues.edge_quic_udp_port);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[10].text().should.become(defaultConfigValues.edge_data_receive_timeout_sec);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[12].text().should.become(defaultConfigValues.app_name);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[14].text().should.become(defaultConfigValues.internal_domains_black_list);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[16].text().should.become(defaultConfigValues.a_b_testing_origin_offload_ratio);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[18].text().should.become(defaultConfigValues.sdk_release_version);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[20].text().should.become(defaultConfigValues.transport_monitoring_url);
            });
    });
});

