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
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    Functions = require("./../../../page_objects/RevTester/functions"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('toggleNetwork', Functions.toggleNetwork);
wd.addPromiseChainMethod('getConfigurationPage', App.getConfigurationPage);
wd.addPromiseChainMethod('getStatsReportingInterval', Config.getStatsReportingInterval);

describe("Smoke: config survival", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds85 = config.get('statsReportingIntervalSeconds85');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var serverConfig = serverConfigs.local;

    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidTester;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');

    before(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds85);
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .toggleNetwork(driver)
            .quit();
    });

    it("should save config after reloading the App", function () {
        //run app, wait for config
        //turn off network
        //restart app
        //check that loaded config is still there and it's not default
        //turn on network
        return driver
            .getConfigurationPage(driver)
            .toggleNetwork(driver)
            // RESET APPk
            .closeApp()
            .launchApp()
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .getConfigurationPage(driver)
            .getStatsReportingInterval(driver)
            .then(function (statsReportingInterval) {
                return statsReportingInterval.text().should.become(statsReportingIntervalSeconds85 + "");
            })
    });
});

