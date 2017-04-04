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
    actions = require("./../../../helpers/actions"),
    serverConfigs = require('./../../../helpers/appium-servers'),
    configDefaultValues = require("./../../../config/default").values,
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    Menu = require("./../../../page_objects/RevDemo/mainNavigation"),
    Configuration = require("./../../../page_objects/RevDemo/configurationPage"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('swipe', actions.swipe);

describe("Smoke Configuration", function () {
    this.timeout(configDefaultValues.describeTimeout);
    var driverRevTester = undefined;
    var driverRevDemo = undefined;
    var portalAPIKey = configDefaultValues.portalAPIKey;
    var appId = configDefaultValues.appId;
    var accountId = configDefaultValues.accountId;
    var statsReportingIntervalSeconds60 = configDefaultValues.statsReportingIntervalSeconds60;
    var configurationRefreshIntervalMiliSec = 60000;

    before(function () {
        var serverConfig = serverConfigs.local;
        driverRevTester = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevTester);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        //run RevTester then turn off the network using RevTester and then quit RevTester
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(6000)
            .swipe({
                startX: 300, startY: 50,
                endX: 300, endY: 500,
                duration: 1500
            })
            .sleep(2000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 500
            })
            .sleep(1000)
            .swipe({
                startX: 1300, startY: 150,
                endX: 1300, endY: 150,
                duration: 500
            })
            .sleep(1000)
            .swipe({
                startX: 300, startY: 2000,
                endX: 300, endY: 50,
                duration: 1500
            })
            .sleep(2000)
            .quit();
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        //turn on the network and quit RevDemo
        return driverRevDemo
            .sleep(1000)
            .swipe({
                startX: 500, startY: 50,
                endX: 500, endY: 500,
                duration: 1500
            })
            .sleep(2000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 500
            })
            .sleep(1000)
            .swipe({
                startX: 1300, startY: 150,
                endX: 1300, endY: 150,
                duration: 500
            })
            .sleep(1000)
            .swipe({
                startX: 300, startY: 2000,
                endX: 300, endY: 50,
                duration: 1500
            })
            .sleep(2000)
            .quit();
    });


    it("should have a defauld config on the first initizlization and cannot load config from API", function () {
        //run RevDemo
        var serverConfig = serverConfigs.local;
        driverRevDemo = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevDemo);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidApiDemos;
        //check that it has default config
        return driverRevDemo
            .init(desired)
            .setImplicitWaitTimeout(6000)
            .sleep(1000)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .sleep(1000)
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .sleep(1000)
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[2].text().should.become(configDefaultValues.defaultConfig.stats_reporting_interval_sec);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[4].text().should.become(configDefaultValues.defaultConfig.stats_reporting_level);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[6].text().should.become(configDefaultValues.defaultConfig.edge_failures_failover_threshold_percent);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[8].text().should.become(configDefaultValues.defaultConfig.edge_quic_udp_port);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[10].text().should.become(configDefaultValues.defaultConfig.edge_data_receive_timeout_sec);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[12].text().should.become(configDefaultValues.defaultConfig.app_name);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[14].text().should.become(configDefaultValues.defaultConfig.internal_domains_black_list);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[16].text().should.become(configDefaultValues.defaultConfig.a_b_testing_origin_offload_ratio);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[18].text().should.become(configDefaultValues.defaultConfig.sdk_release_version);
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[20].text().should.become(configDefaultValues.defaultConfig.transport_monitoring_url);
            });
    });
});

