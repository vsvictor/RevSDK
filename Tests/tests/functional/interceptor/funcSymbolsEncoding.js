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
wd.addPromiseChainMethod('getResponseBody', App.getResponseBody);
wd.addPromiseChainMethod('waitForResponse', Settings.waitForResponse);

describe("Functional Interceptor", function () {
    describe("Different symbols in URL (Encoding)", function () {
        var describeTimeout = config.get('describeTimeout');
        this.timeout(describeTimeout);
        var driver = undefined;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        var portalAPIKey = config.get('portalAPIKey');
        var appIdTester = config.get('appIdTester');
        var accountId = config.get('accountId');
        var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
        var encoding = config.get('encoding');

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

        it("should check that SDK handles German symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .getInputUrl(driver)
                .sendKeys(encoding.german.site)
                .clickSendBtn(driver)
                .waitForResponse(driver)
                .getResponseBody(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.german.value)
                })
        });

        it("should check that SDK handles Russian symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .getInputUrl(driver)
                .sendKeys(encoding.russian.site)
                .clickSendBtn(driver)
                .waitForResponse(driver)
                .getResponseBody(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.russian.value)
                })
        });

        it("should check that SDK handles Turkish symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .getInputUrl(driver)
                .sendKeys(encoding.turkish.site)
                .clickSendBtn(driver)
                .waitForResponse(driver)
                .getResponseBody(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.turkish.value)
                })
        });

        it("should check that SDK handles Chinese symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .getInputUrl(driver)
                .sendKeys(encoding.chinese.site)
                .clickSendBtn(driver)
                .waitForResponse(driver)
                .getResponseBody(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.chinese.value)
                })
        });

        it("should check that SDK handles Arabic symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .getInputUrl(driver)
                .sendKeys(encoding.arabic.site)
                .clickSendBtn(driver)
                .waitForResponse(driver)
                .getResponseBody(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.arabic.value)
                })
        });

        it("should check that SDK handles German symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .getInputUrl(driver)
                .sendKeys(encoding.german.site)
                .clickSendBtn(driver)
                .waitForResponse(driver)
                .getResponseBody(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.german.value)
                })
        });
    });
});

