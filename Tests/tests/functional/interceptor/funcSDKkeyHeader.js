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
wd.addPromiseChainMethod('setModeTransferAndReport', App.setModeTransferAndReport);
wd.addPromiseChainMethod('getInputUrl', App.getInputUrl);
wd.addPromiseChainMethod('clickSendBtn', App.clickSendBtn);
wd.addPromiseChainMethod('getResponseHeaders', App.getResponseHeaders);
wd.addPromiseChainMethod('waitForResponse', Settings.waitForResponse);

describe("Functional: interceptor. Adding SDK key to the headers. Transfer_only mode", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var domainsWhiteList = config.get('domainsWhiteList');
    var domainsBlackList = config.get('domainsBlackList');
    var domainsProvisionedList = config.get('domainsProvisionedList');
    var emptyDomainsWhiteList = [];
    var emptyDomainsBlackList = [];
    var headerRevSDK = config.get('headerRevSDK');
    var SDKkeyTester = config.get('SDKkeyTester');
    var httpWebsite = config.get('httpWebsite');

    var serverConfig = serverConfigs.local;
    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidTester;

    afterEach(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("should check that domain from 'PROVISIONED' list won't return SDK key in headers.transfer_only", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            emptyDomainsWhiteList, emptyDomainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .getInputUrl(driver)
            .sendKeys(domainsProvisionedList[0])
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(SDKkeyTester + headerRevSDK)
            })
    });

    it("should check that domain from 'PROVISIONED' list won't return SDK key in headers.transfer_and_report", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            emptyDomainsWhiteList, emptyDomainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferAndReport(driver)
            .getInputUrl(driver)
            .sendKeys(domainsProvisionedList[0])
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(SDKkeyTester + headerRevSDK)
            })
    });

    it("should check that domain from 'WHITE' list will return SDK key in headers.transfer_only", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList, domainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .getInputUrl(driver)
            .sendKeys(domainsWhiteList[0])
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.eventually.include(SDKkeyTester + headerRevSDK)
            })
    });

    it("should check that domain from 'WHITE' list will return SDK key in headers.transfer_and_report", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList, domainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferAndReport(driver)
            .getInputUrl(driver)
            .sendKeys(domainsWhiteList[0])
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.eventually.include(SDKkeyTester + headerRevSDK)
            })
    });

    it("should check that if domain 'WHITE' list is empty it will return SDK key in headers.transfer_only", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            emptyDomainsWhiteList, domainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .getInputUrl(driver)
            .sendKeys(httpWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.eventually.include(SDKkeyTester + headerRevSDK)
            })
    });

    it("should check that if domain 'WHITE' list is empty it will return SDK key in headers.transfer_and_report", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            emptyDomainsWhiteList, domainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferAndReport(driver)
            .getInputUrl(driver)
            .sendKeys(httpWebsite)
            .clickSendBtn(driver)
            .waitForResponse(driver)
            .getResponseHeaders(driver)
            .then(function (headers) {
                return headers.text().should.eventually.include(SDKkeyTester + headerRevSDK)
            })
    });
});


