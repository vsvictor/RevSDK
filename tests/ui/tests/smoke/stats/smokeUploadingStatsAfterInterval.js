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
    Functions = require("./../../../page_objects/RevTester/functions"),
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    Counters = require("./../../../page_objects/RevTester/openDrawerPage"),
    Waits = require("./../../../page_objects/RevTester/waits"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
wd.addPromiseChainMethod('closeCountersPage', App.closeCountersPage);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseHeadersFieldValue', httpFields.getResponseHeadersFieldValue);
wd.addPromiseChainMethod('getTotalStatsRequestUploaded', Counters.getTotalStatsRequestUploaded);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);
wd.addPromiseChainMethod('clickSendStatsBtn', App.clickSendStatsBtn);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);

describe("Smoke => Stats: ", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout * 2);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var httpWebsite = config.get('httpWebsite');
    var domainsWhiteList = config.get('domainsWhiteList');
    var domainsBlackList = undefined;
    var domainsProvisionedList = undefined;
    
    var fromTimeStamp = new Date();;
    fromTimeStamp = fromTimeStamp.getTime();
    var dateNow = new Date();
    var dateNowPlusTwoMinutes = new Date();
    dateNowPlusTwoMinutes.setMinutes(dateNow.getMinutes() + 3);
    var toTimeStamp = dateNowPlusTwoMinutes;
    toTimeStamp = toTimeStamp.getTime();

    beforeEach(function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList, domainsBlackList = [], domainsProvisionedList = []);

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

    afterEach(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .waitForResponse(driver)
            .quit();
    });

    it("Uploading stats again after stats_reporting_interval (60s)", function () {
        return driver
            .getCountersPage(driver)
            .getTotalStatsRequestUploaded(driver)
            .then(function (requestCountOne) {
                return driver
                    .closeCountersPage(driver)
                    .clickSendStatsBtn(driver)
                    .then(function () {
                        return driver
                            .getCountersPage(driver)
                            .getTotalStatsRequestUploaded(driver)
                            .then(function (requestCountTwo) {
                                return (requestCountTwo > requestCountOne) ?
                                    driver
                                        .closeCountersPage(driver)
                                        .sleep(statsReportingIntervalSeconds60 * 500)
                                        .getCountersPage(driver)
                                        .closeCountersPage(driver)
                                        .sleep(statsReportingIntervalSeconds60 * 500)
                                        .getCountersPage(driver)
                                        .getTotalStatsRequestUploaded(driver)
                                        .then(function (requestCountThree) {
                                            return requestCountThree > requestCountTwo;
                                        })
                                    : false;
                            }).should.become(true);
                    })
            });
    });

     it("Uploading stats on stats API after stats_reporting_interval", function () {
        return driver
        // send request on httpWebsite
            .setModeTransferAndReport(driver)
            .sendRequestOnURL(driver, httpWebsite)
        // wait till stats are sent
        // (the reason why we use some functions between sleeps is because when we are
        // 'sleeping' during 60 secs or more appium losts connection with emulator)
            .sleep(statsReportingIntervalSeconds60 * 1000 / 2)
            .setModeTransferAndReport(driver)
            .sleep(statsReportingIntervalSeconds60 * 1000 / 2)
            .setModeTransferAndReport(driver)
            .sleep(statsReportingIntervalSeconds60 * 1000 / 2)
            .setModeTransferAndReport(driver)
            .sleep(statsReportingIntervalSeconds60 * 1000 / 2)
            .setModeTransferAndReport(driver)
            .sleep(statsReportingIntervalSeconds60 * 1000 / 2)
            .setModeTransferAndReport(driver)
            .sleep(statsReportingIntervalSeconds60 * 1000 / 2)

            .then(function () {
            // get total hits from stats API for the last 3 minutes 
            // and check that total hits is more than 0
                request.getStatsOfAppDuringInterval(appIdTester, portalAPIKey, fromTimeStamp, toTimeStamp);
                return driver
                    .waitForResponse(driver)
                    .then(function () {
                        var responseBody = request.getResponseBodyFromStatsOfAppDuringInterval();
                        return responseBody.data.hits > 0;
                    })
                    .should.become(true);
            })
    });
});

