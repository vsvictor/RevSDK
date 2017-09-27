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
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    Functions = require("./../../../page_objects/RevTester/functions"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseHeadersFieldValue', httpFields.getResponseHeadersFieldValue);

describe("Functional: interceptor. Operation modes transfer_and_report and transfer_only. domain is out of white list", function () {
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
    var headerRev = config.get('headerRev');
    var httpsWebsite = config.get('httpsWebsite');

    before(function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList, domainsBlackList, domainsProvisionedList);

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
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

   it("should check that if domain is not included in 'WHITE' list it won't return Rev Headers. transfer_only", function () {
        return driver
        
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpsWebsite)
            .getResponseHeadersFieldValue(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            });
    });

    it("should check that if domain is not included in 'WHITE' list it won't return Rev Headers. transfer_and_report", function () {
        return driver
            .setModeTransferAndReport(driver)
            .sendRequestOnURL(driver, httpsWebsite)
            .getResponseHeadersFieldValue(driver)
            .then(function (headers) {
                return headers.text().should.not.eventually.include(headerRev)
            });
    });
});


