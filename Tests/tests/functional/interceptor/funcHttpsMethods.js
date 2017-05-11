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
    request = require("./../../../helpers/requests"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    Settings = require("./../../../page_objects/RevTester/settings");
wd.addPromiseChainMethod('setModeTransferAndReport', App.setModeTransferAndReport);
wd.addPromiseChainMethod('setHttpMethodGET', App.setHttpMethodGET);
wd.addPromiseChainMethod('setHttpMethodPOST', App.setHttpMethodPOST);
wd.addPromiseChainMethod('setHttpMethodPUT', App.setHttpMethodPUT);
wd.addPromiseChainMethod('setHttpMethodDELETE', App.setHttpMethodDELETE);
wd.addPromiseChainMethod('setHttpMethodHEAD', App.setHttpMethodHEAD);
wd.addPromiseChainMethod('setHttpMethodTRACE', App.setHttpMethodTRACE);
wd.addPromiseChainMethod('setHttpMethodOPTIONS', App.setHttpMethodOPTIONS);
wd.addPromiseChainMethod('setHttpMethodCONNECT', App.setHttpMethodCONNECT);
wd.addPromiseChainMethod('setModeOff', App.setModeOff);
wd.addPromiseChainMethod('getInputUrl', App.getInputUrl);
wd.addPromiseChainMethod('clickSendBtn', App.clickSendBtn);
wd.addPromiseChainMethod('waitForResponse', Settings.waitForResponse);
wd.addPromiseChainMethod('getResponseBody', App.getResponseBody);

describe("Functional: interceptor. check that response bodies with and without SDK are identical.", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var httpsWebsite = config.get('httpsWebsite');

    before(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);

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

    after(function () {
        return driver
            .quit();
    });

    it("should check HTTPS Method GET", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodGET(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodGET(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method POST", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodPOST(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodPOST(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method PUT", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodPUT(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodPUT(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method DELETE", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodDELETE(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodDELETE(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method HEAD", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodHEAD(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodHEAD(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method CONNECT", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodCONNECT(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodCONNECT(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method OPTIONS", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodOPTIONS(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodOPTIONS(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });

    it("should check HTTPS Method TRACE", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodTRACE(driver)
            .getInputUrl(driver)
            .sendKeys(httpsWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseBody(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodTRACE(driver)
                    .getInputUrl(driver)
                    .sendKeys(httpsWebsite)
                    .clickSendBtn(driver)
                    .waitForResponse(driver)
                    .getResponseBody(driver)
                    .then(function (repsonseOffMode) {
                        return repsonseOffMode.text().should.become(repsonseTransferMode);
                    });
            });
    });
});


