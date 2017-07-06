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
    App = require("./../../../page_objects/RevTester/mainPage"),
    Counters = require("./../../../page_objects/RevTester/openDrawerPage"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);
wd.addPromiseChainMethod('getCounterTotalRequestsStandard', Counters.getCounterTotalRequestsStandard);

describe("Functional: allowed_transport_protocol 'standard' is used", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var allowedProtocolsStandard = config.get('allowedProtocolsStandard');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var httpWebsite = config.get('httpWebsite');

    before(function () {
        request.putConfigWithAllowedProtocols(appIdTester, portalAPIKey, accountId, allowedProtocolsStandard);

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

    it("should", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpWebsite)
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpWebsite)
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpWebsite)
            .getCountersPage(driver)
            .getCounterTotalRequestsStandard(driver)
            .then(function (totalRequestsStandard) {
                totalRequestsStandard = Number(totalRequestsStandard);
                return totalRequestsStandard.should.equal(6);
            })
    });
});



