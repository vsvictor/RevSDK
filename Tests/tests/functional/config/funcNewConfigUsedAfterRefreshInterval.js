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
    config = require("config"),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    Settings = require("./../../../page_objects/RevTester/settings"),
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('setModeTransferOnly', App.setModeTransferOnly);
wd.addPromiseChainMethod('getInputUrl', App.getInputUrl);
wd.addPromiseChainMethod('clickSendBtn', App.clickSendBtn);
wd.addPromiseChainMethod('getResponseHeaders', App.getResponseHeaders);
wd.addPromiseChainMethod('waitForResponse', Settings.waitForResponse);
wd.addPromiseChainMethod('waitHalfConfigRefreshInterval', Settings.waitHalfConfigRefreshInterval);

describe("Functional: loaded config is used after refresh interval", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var domainsBlackList = config.get('domainsBlackList');
    var domainsWhiteList = config.get('domainsWhiteList');
    var headerRev = config.get('headerRev');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');

    before(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

   it("should check that new loaded config is used (not default) after config_refresh_interval", function () {
        return driver
            .waitForResponse(driver)
            .then(function () {
                request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
                    domainsWhiteList, domainsBlackList, []);
            })
            //wait for new config to load after config_refresh_interval
            .waitHalfConfigRefreshInterval(driver)
            .setModeTransferOnly(driver)
            .waitHalfConfigRefreshInterval(driver)
            .getInputUrl(driver)
            .sendKeys(domainsBlackList[1])
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            })
    });
});


