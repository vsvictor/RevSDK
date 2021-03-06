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
    Counters = require("./../../../page_objects/RevTester/openDrawerPage"),
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    Waits = require("./../../../page_objects/RevTester/waits"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseBodyFieldValue', httpFields.getResponseBodyFieldValue);
wd.addPromiseChainMethod('getRevRequests', Counters.getRevRequests);
wd.addPromiseChainMethod('getOriginRequests', Counters.getOriginRequests);
wd.addPromiseChainMethod('getCounterRequestCount', Counters.getCounterRequestCount);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);
wd.addPromiseChainMethod('closeCountersPage', App.closeCountersPage);
wd.addPromiseChainMethod('getConfigurationPage', App.getConfigurationPage);
wd.addPromiseChainMethod('getDomainsInternalBlackList', Config.getDomainsInternalBlackList);
wd.addPromiseChainMethod('getMainPage', App.getMainPage);



describe("Functional => interceptor: ", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var domainsInternalBlackList = config.get('domainsInternalBlackList');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var appId = config.get('appId');
    var portalAPIKey = config.get('portalAPIKey');
    var accountId = config.get('accountId');

    before(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        
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
            .waitForResponse(driver)
            .quit();
    });

    it("Check internal black list for 'transfer only' mode", function () {
        return driver
            .getConfigurationPage(driver)
            .getDomainsInternalBlackList(driver)
            .then(function (domainsList) {
                return domainsList.text().then(function (domains) {
                    // if there is the domain
                    if (JSON.parse(domains).indexOf(domainsInternalBlackList[0]) !== -1) {
                        return driver
                            .getMainPage(driver)
                            .getCountersPage(driver)
                            .getOriginRequests(driver)
                            .then(function (originRequestsFirst) {
                                return driver
                                    .closeCountersPage(driver)
                                    .setModeTransferOnly(driver)
                                    .sendRequestOnURL(driver, domainsInternalBlackList[0])
                                    .then(function () {
                                        return driver
                                            .getMainPage(driver)
                                            .getCountersPage(driver)
                                            .getOriginRequests(driver)
                                            .then(function (originRequestsLast) {
                                                return ~~originRequestsFirst < ~~originRequestsLast;
                                            }).should.become(true);
                                    });
                            });
                    } else {
                        console.log(colors.red(massages.noDomainExists));
                        return domainsList.text().should.become(domainsBlackList);
                    }
                });
            });   

    });
});




