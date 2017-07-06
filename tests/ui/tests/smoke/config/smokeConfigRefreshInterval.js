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
    config = require("config"),
    actions = require("./../../../helpers/actions"),
    serverConfigs = require('./../../../helpers/appium-servers'),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    Waits = require("./../../../page_objects/RevTester/waits"),
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('getStatsReportingInterval', Config.getStatsReportingInterval);
wd.addPromiseChainMethod('clickMenuButton', App.clickMenuButton);
wd.addPromiseChainMethod('clickConfigViewButton', App.clickConfigViewButton);
wd.addPromiseChainMethod('waitHalfConfigRefreshInterval', Waits.waitHalfConfigRefreshInterval);

describe("Smoke: configuration refresh interval", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds83 = config.get('statsReportingIntervalSeconds83');
    var statsReportingIntervalSeconds84 = config.get('statsReportingIntervalSeconds84');
    var statsReportingIntervalSeconds87 = config.get('statsReportingIntervalSeconds87');

    beforeEach(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
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

    afterEach(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("should load config from API after configuration_refresh_interval (60 secs)", function () {
        return driver
            .waitHalfConfigRefreshInterval(driver)
            //change config on API 30 seconds after we run app
            .then(function () {
                request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds83);
                request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds83);
            })
            //check that sdk will load new config after config_refresh interval secs
            .clickMenuButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickConfigViewButton(driver)
            .getStatsReportingInterval(driver)
            .then(function (statsReportingInterval) {
                return statsReportingInterval.text().should.become(statsReportingIntervalSeconds60.toString());
            });
    });

    it("should check that config reloads after config_refresh interval*2 secs", function () {
        return driver
            .waitHalfConfigRefreshInterval(driver)
            .clickMenuButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickConfigViewButton(driver)
            .waitHalfConfigRefreshInterval(driver)
    //change config on API 90 seconds after we run app
            .then(function () {
                request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds84);
                request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds84);
            })
    //check that sdk will load new config after config_refresh interval*2 secs
            .clickMenuButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickConfigViewButton(driver)
            .getStatsReportingInterval(driver)
            .then(function (statsReportingInterval) {
                return statsReportingInterval.text().should.become(statsReportingIntervalSeconds87.toString());
            });
    });

    it("should check that config reloads after config_refresh interval*3 secs", function () {
        return driver
            .waitHalfConfigRefreshInterval(driver)
            .clickMenuButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickConfigViewButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickMenuButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickConfigViewButton(driver)
            .waitHalfConfigRefreshInterval(driver)
    //change config on API 150 seconds after we run app
            .then(function () {
                request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds87);
                request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds87);
            })
    //check that sdk will load new config after config_refresh interval*3 secs
            .clickMenuButton(driver)
            .waitHalfConfigRefreshInterval(driver)
            .clickConfigViewButton(driver)
            .getStatsReportingInterval(driver)
            .then(function (statsReportingInterval) {
                return statsReportingInterval.text().should.become(statsReportingIntervalSeconds87.toString());
            });
    });
});
