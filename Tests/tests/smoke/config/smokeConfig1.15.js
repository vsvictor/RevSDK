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
    Menu = require("./../../../page_objects/RevDemo/mainNavigation"),
    ConfigurationPage = require("./../../../page_objects/RevDemo/configurationPage"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('swipe', actions.swipe);

describe("Smoke Configuration", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driverRevTester = undefined;
    var driverRevDemo = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds82 = config.get('statsReportingIntervalSeconds82');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var defaultConfig = config.get('defaultConfig');

    before(function () {
        var serverConfig = serverConfigs.local;
        driverRevTester = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevTester);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        //run RevTester then turn off the network using RevTester and then quit RevTester
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .sleep(1000)
            .swipe({
                startX: 600, startY: 50,
                endX: 600, endY: 1000,
                duration: 600
            })
            .sleep(3000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 350
            })
            .sleep(4000)
            .swipe({
                startX: 800, startY: 150,
                endX: 800, endY: 150,
                duration: 350
            })
            .sleep(3000)
            .swipe({
                startX: 300, startY: 2000,
                endX: 300, endY: 50,
                duration: 1000
            })
            .sleep(4000)
            .quit();
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        //turn on the network and quit RevDemo
        return driverRevDemo
            .sleep(1000)
            .swipe({
                startX: 600, startY: 50,
                endX: 600, endY: 1000,
                duration: 600
            })
            .sleep(3000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 350
            })
            .sleep(4000)
            .swipe({
                startX: 800, startY: 150,
                endX: 800, endY: 150,
                duration: 350
            })
            .sleep(3000)
            .swipe({
                startX: 300, startY: 2000,
                endX: 300, endY: 50,
                duration: 1500
            })
            .sleep(4000)
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
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[2].text().should.become(defaultConfig.stats_reporting_interval_sec);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[4].text().should.become(defaultConfig.stats_reporting_level);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[6].text().should.become(defaultConfig.edge_failures_failover_threshold_percent);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[8].text().should.become(defaultConfig.edge_quic_udp_port);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[10].text().should.become(defaultConfig.edge_data_receive_timeout_sec);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[12].text().should.become(defaultConfig.app_name);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[14].text().should.become(defaultConfig.internal_domains_black_list);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[16].text().should.become(defaultConfig.a_b_testing_origin_offload_ratio);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[18].text().should.become(defaultConfig.sdk_release_version);
            })
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[20].text().should.become(defaultConfig.transport_monitoring_url);
            });
    });
});

