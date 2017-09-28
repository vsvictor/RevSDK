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
    Functions = require("./../../../page_objects/RevTester/functions"),
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    Waits = require("./../../../page_objects/RevTester/waits"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseHeadersFieldValue', httpFields.getResponseHeadersFieldValue);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);

describe("Functional: interceptor. Black-listed domains take precedence over all other domain configs", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var headerRev = config.get('headerRev');
    var domainsWhiteList = config.get('domainsWhiteList');
    var domainsProvisionedList = config.get('domainsProvisionedList');
    var domainsBlackList = config.get('domainsBlackList');
    var domainsInternalBlackList = config.get('domainsInternalBlackList');

    var serverConfig = serverConfigs.local;
    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidTester;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');

    afterEach(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .waitForResponse(driver)
            .quit();
    });

    it("should check that if domain is listed in PROVISIONED,BLACK lists it won't return Rev Headers", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList , domainsProvisionedList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, domainsProvisionedList[0])
            .waitForResponse(driver)
            .getResponseHeadersFieldValue(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            })
    });

    it("should check that if domain is listed in 'WHITE','BLACK' lists it won't return Rev Headers", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList,  domainsWhiteList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, domainsWhiteList[0])
            .waitForResponse(driver)
            .getResponseHeadersFieldValue(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            })
    });

    it("should check that if domain is listed in 'WHITE','Internal BLACK' lists it won't return Rev Headers", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsInternalBlackList,  domainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, domainsInternalBlackList[0])
            .waitForResponse(driver)
            .getResponseHeadersFieldValue(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            })
    });

    it("should check that if domain is listed in 'PROVISIONED','Internal BLACK' lists it won't return Rev Headers", function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList,  domainsBlackList, domainsInternalBlackList);

        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, domainsInternalBlackList[0])
            .waitForResponse(driver)
            .getResponseHeadersFieldValue(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            })
    });

});


