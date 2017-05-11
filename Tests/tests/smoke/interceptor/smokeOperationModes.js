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
wd.addPromiseChainMethod('setModeReportOnly', App.setModeReportOnly);
wd.addPromiseChainMethod('setModeOff', App.setModeOff);
wd.addPromiseChainMethod('getInputUrl', App.getInputUrl);
wd.addPromiseChainMethod('clickSendBtn', App.clickSendBtn);
wd.addPromiseChainMethod('getResponseHeaders', App.getResponseHeaders);
wd.addPromiseChainMethod('waitForResponse', Settings.waitForResponse);

describe("Smoke: interceptor. Operation modes off and report_only", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var appIdTester = config.get('appIdTester');
    var portalAPIKey = config.get('portalAPIKey');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var headerRev = config.get('headerRev');
    var httpWebsite = config.get('httpWebsite');

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
        return driver
            .quit();
    });

    it("should check that using operation mode 'report only' there are not headers in response", function () {
        return driver
            .setModeReportOnly(driver)
            .getInputUrl(driver)
            .sendKeys(httpWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                 return headers.text().should.not.eventually.include(headerRev)
            });
    });

    it("should check that using operation mode 'off' there are not headers in response", function () {
        return driver
            .setModeOff(driver)
            .getInputUrl(driver)
            .sendKeys(httpWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            });
    });
});


