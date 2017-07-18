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
    Functions = require("./../../../page_objects/RevTester/functions"),
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    Counters = require("./../../../page_objects/RevTester/openDrawerPage"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseHeadersFieldValue', httpFields.getResponseHeadersFieldValue);
wd.addPromiseChainMethod('getCounterRevRequests', Counters.getCounterRevRequests);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);

describe("Smoke: stats survival", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var serverConfig = serverConfigs.local;
    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidTester;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var httpWebsite = config.get('httpWebsite');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var appId = config.get('appId');
    var portalAPIKey = config.get('portalAPIKey');
    var accountId = config.get('accountId');

    before(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        return driver
            .quit();
    });

    it("should check that stats survive after app restart", function () {
        return driver
            .setModeTransferAndReport(driver)
            .sendRequestOnURL(driver, httpWebsite)
            .getResponseHeadersFieldValue(driver)
    //RESTART app
            .closeApp()
            .launchApp()
    //check that stats survive after app restart
            .getCountersPage(driver)
            .getCounterRevRequests(driver)
            .then(function (revRequests) {
                revRequests = Number(revRequests);
                return revRequests.should.be.above(0);
            });
    });
});




