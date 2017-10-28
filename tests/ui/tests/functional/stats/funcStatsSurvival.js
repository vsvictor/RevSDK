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
    Waits = require("./../../../page_objects/RevTester/waits"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseHeadersFieldValue', httpFields.getResponseHeadersFieldValue);
wd.addPromiseChainMethod('getRevRequests', Counters.getRevRequests);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);

describe("Functional: stats survival", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout*2);
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
    var httpBinWebsiteStatuses = config.get('httpBinWebsiteStatuses');
    var httpBinStatuses = config.get('httpBinStatuses');

    var fromTimeStamp = new Date();;
    fromTimeStamp = fromTimeStamp.getTime();
    var dateNow = new Date();
    var dateNowPlusTwoMinutes = new Date();
    dateNowPlusTwoMinutes.setMinutes(dateNow.getMinutes() + 5);
    var toTimeStamp = dateNowPlusTwoMinutes;
    toTimeStamp = toTimeStamp.getTime();

    before(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        return driver
            .waitForResponse(driver)
            .quit();
    });

    it("should check that stats survive after app restart", function () {
        return driver
        // send two requests on servers to get 400 and 402 response codes
            .setModeTransferAndReport(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[400])
            .waitForResponse(driver)
            .waitForResponse(driver)
            .setModeTransferAndReport(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[402])
            .waitForResponse(driver)
            .waitForResponse(driver)
        //RESTART app
            .closeApp()
            .launchApp()
        // wait till stats are sent
        // (the reason why we use some functions between sleeps is because when we are
        // 'sleeping' during 60 secs or more appium losts connection with emulator)
        // (the reason why we are waiting for such a long period of time is because
        // there is time required to process stats requests from SDK on API)
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
        // check that stats that were collected during last run survived app restart and 
        // were sent to stats API after restart
            .then(function () {
            // get total hits from stats API for the last 3 minutes 
            // and check that total hits is more than 0
                request.getStatsAggFlowWithStatusCode(appId, portalAPIKey, fromTimeStamp, toTimeStamp);
                console.log('fromTimeStamp---', fromTimeStamp);
                console.log('toTimeStamp---', toTimeStamp);
                return driver
                    .waitForResponse(driver)
                    .then(function () {
                        var responseBody = request.getResponseBodyFromStatsAggFlowWithStatusCode();
                        console.log("RESPONSE BODY AGG FLOW");
                        console.log(responseBody);
                        var isHttpCode400exist = false;
                        var isHttpCode402exist = false;
                        // check that response body will contain status codes 400 and 402
                        for (var i = 0; i < responseBody.data.length; i++) {
                            if (responseBody.data[i].key == httpBinStatuses[400]) {
                                console.log("400 EXISTS");
                                isHttpCode400exist = true;
                            }
                            if (responseBody.data[i].key == httpBinStatuses[402]) {
                                console.log("402 EXISTS");
                                isHttpCode402exist = true;
                            }
                        }
                        return isHttpCode402exist === true || isHttpCode400exist === true;
                    })
                    .should.become(true);
            })
    });
});




