


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
var colors = require('colors');
var wd = require("wd"),
    _ = require('underscore'),
    config = require("config"),
    actions = require("./../../../helpers/actions"),
    serverConfigs = require('./../../../helpers/appium-servers'),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    Stats = require("./../../../page_objects/RevTester/statsViewPage"),
    Functions = require("./../../../page_objects/RevTester/functions"),
    Waits = require("./../../../page_objects/RevTester/waits"),
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('toggleNetwork', Functions.toggleNetwork);
wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);
wd.addPromiseChainMethod('getDomainsWhiteList', Config.getDomainsWhiteList);
wd.addPromiseChainMethod('getRevRequests', Stats.getRevRequests);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);
wd.addPromiseChainMethod('scrollDown', actions.scrollDown);
wd.addPromiseChainMethod('closeCountersPage', App.closeCountersPage);
wd.addPromiseChainMethod('getConfigurationPage', App.getConfigurationPage);
wd.addPromiseChainMethod('getMainPage', App.getMainPage);
wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
//wd.addPromiseChainMethod('clickFetchConfigBtn', App.clickFetchConfigBtn);

describe("Function => interceptor: ", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var appId = config.get('appId');
    var serverConfig = serverConfigs.local;
    var massages = config.get('massages');

    var appIdTester = config.get('appIdTester');
    var portalAPIKey = config.get('portalAPIKey');
    var accountId = config.get('accountId');
    var domainsWhiteList = config.get('domainsWhiteList');
    var domainsBlackList = config.get('domainsBlackList');
    var domainsProvisionedList = config.get('domainsProvisionedList');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds85 = config.get('statsReportingIntervalSeconds85');

    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidTester;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');

    beforeEach(function () {
        request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
            domainsWhiteList, domainsBlackList, domainsProvisionedList);

        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    afterEach(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("if domain is listed in 'domains_white_list' of "+
        "'Configuration view' for 'transfer only' mode", function () {
         request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
             domainsWhiteList, domainsBlackList, domainsProvisionedList);

        return driver
            .waitForResponse(driver)
            //.clickFetchConfigBtn(driver)
            //.sleep(80000)
            .getConfigurationPage(driver)
            .getDomainsWhiteList(driver)
            .then(function (domainsList) {

                return domainsList.text().then(function (domains) {

                    // if there is the domain
                    console.log('CHECK ===>>> ', domains);
                    //if (JSON.parse(domains).indexOf(domainsWhiteList[1]) !== -1) {
                        return driver
                            .getMainPage(driver)
                            .getCountersPage(driver)
                            .getRevRequests(driver)
                            .then(function (valueFirst) {
                                return driver
                                    .closeCountersPage(driver)
                                    .setModeTransferOnly(driver)
                                    .sendRequestOnURL(driver, domainsWhiteList[1])
                                    .then(function () {
                                        return driver
                                            .getCountersPage(driver)
                                            .getRevRequests(driver)
                                            .then(function (valueLast) {
                                                return ~~valueFirst < ~~valueLast;
                                            }).should.become(true);
                                    });
                            });
                   //} else {
                       // console.log(colors.red(massages.noDomainExists));
                       // return domainsList.text().should.become(domainsWhiteList);
                    //}
                });
            });

    });




});



