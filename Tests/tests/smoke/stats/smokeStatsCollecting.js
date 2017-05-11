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
    Settings = require("./../../../page_objects/RevTester/settings"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('setModeTransferAndReport', App.setModeTransferAndReport);
wd.addPromiseChainMethod('getInputUrl', App.getInputUrl);
wd.addPromiseChainMethod('clickSendBtn', App.clickSendBtn);
wd.addPromiseChainMethod('getResponseHeaders', App.getResponseHeaders);
wd.addPromiseChainMethod('clickMenuButton', App.clickMenuButton);
wd.addPromiseChainMethod('getStatsValueRequests', App.getStatsValueRequests);
wd.addPromiseChainMethod('clickStatsViewButton', App.clickStatsViewButton);
wd.addPromiseChainMethod('waitForResponse', Settings.waitForResponse);

describe("Smoke: stats collecting", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var httpWebsite = config.get('httpWebsite');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var appId = config.get('appId');
    var portalAPIKey = config.get('portalAPIKey');
    var accountId = config.get('accountId');

    before(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        return driver
            .quit();
    });

    it("should collect stats when we send requests", function () {
        return driver
            .setModeTransferAndReport(driver)
            .getInputUrl(driver)
            .sendKeys(httpWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .clickMenuButton(driver)
            .clickStatsViewButton(driver)
            .getStatsValueRequests(driver)
            .then(function (revRequests) {
                var revRequestsValue = Number(revRequests);
                return revRequestsValue.should.be.above(0);
            });
    });
});




