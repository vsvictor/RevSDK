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
    Stats = require("./../../../page_objects/RevTester/statsViewPage"),
    Functions = require("./../../../page_objects/RevTester/functions"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('toggleNetwork', Functions.toggleNetwork);
wd.addPromiseChainMethod('getStatsPage', App.getStatsPage);
wd.addPromiseChainMethod('getStatsReportingInterval', Config.getStatsReportingInterval);
wd.addPromiseChainMethod('getSdkKey', Stats.getSdkKey);

describe("Smoke: config survival", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds85 = config.get('statsReportingIntervalSeconds85');
    var serverConfig = serverConfigs.local;

    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidTester;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');

    beforeEach(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds85);
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    afterEach(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("Two apps with the same SDK key. Run first app - check config."+
        " Run second app - check config", function () {
        return driver
            .getStatsPage(driver)
            .getSdkKey(driver)
            .then(function (sdkKeyFirstApp) {
                return driver
                    .closeApp()
                    .launchApp()
                    .getStatsPage(driver)
                    .getSdkKey(driver)
                    .then(function (sdkKeyLastApp) {
                        sdkKeyLastApp.text().then(function (value) {
                            return sdkKeyFirstApp.text().should.become(value);
                        });
                    })
            });
    });


    it("Two apps with different SDK keys. Run first app - check config."+
        " Run second app - check config", function () {
        return driver
            .getStatsPage(driver)
            .getSdkKey(driver)
            .then(function (sdkKeyFirstApp) {
                return sdkKeyFirstApp.text().then(function (valueFirst) {
                    desired.app = apps.androidTesterInvalidSDKkey;
                    return driver
                        .quit().then(function () {
                            return driver
                                .init(desired)
                                .getStatsPage(driver)
                                .getSdkKey(driver)
                                .then(function (sdkKeyLastApp) {
                                    return sdkKeyLastApp.text().then(function (valueLast) {
                                        return valueFirst !== valueLast;
                                    }).should.become(true);
                                });
                        });
                });
            });
    });




});

