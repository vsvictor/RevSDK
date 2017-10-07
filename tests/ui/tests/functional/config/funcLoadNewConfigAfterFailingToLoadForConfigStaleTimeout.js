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
    Config = require("./../../../page_objects/RevTester/configViewPage"),
    request = require("./../../../helpers/requests"),
    Waits = require("./../../../page_objects/RevTester/waits");

wd.addPromiseChainMethod('setModeReportOnly', Modes.setModeReportOnly);
wd.addPromiseChainMethod('closeCountersPage', App.closeCountersPage);
wd.addPromiseChainMethod('clickFetchConfigBtn', App.clickFetchConfigBtn);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseHeadersFieldValue', httpFields.getResponseHeadersFieldValue);
wd.addPromiseChainMethod('getTotalStatsRequestUploaded', Counters.getTotalStatsRequestUploaded);
wd.addPromiseChainMethod('getCountersPage', App.getCountersPage);
wd.addPromiseChainMethod('getConfigurationPage', App.getConfigurationPage);
wd.addPromiseChainMethod('getStatsReportingInterval', Config.getStatsReportingInterval);
wd.addPromiseChainMethod('clickSendStatsBtn', App.clickSendStatsBtn);
wd.addPromiseChainMethod('toggleNetwork', Functions.toggleNetwork);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);

describe("Functional => config: ", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout*2);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var configurationStaleTimeoutSec140 = config.get('configurationStaleTimeoutSec140');
    var statsReportingIntervalSeconds82 = config.get('statsReportingIntervalSeconds82');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var domainsWhiteList = config.get('domainsWhiteList');
    var domainsBlackList = undefined;
    var domainsProvisionedList = undefined;
    var quaterOfConfigStaleTimeoutMilisecs = configurationStaleTimeoutSec140 / 4 * 1000;

    before(function () {
        request.putConfigWithConfigStaleTimeoutSec(appIdTester, portalAPIKey, accountId, configurationStaleTimeoutSec140);

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
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .waitForResponse(driver)
            .quit();
    });

    it("Loading config when switched internet to 'off' after interval (60s)", function () {
        return driver
            //turn off the internet
            .toggleNetwork(driver)
            //we are waiting for config_stale-timeout-secs with turned off internet
            // to fail config getting
            .sleep(quaterOfConfigStaleTimeoutMilisecs)
            .getCountersPage(driver)
            .sleep(quaterOfConfigStaleTimeoutMilisecs)
            .closeCountersPage(driver)
            .sleep(quaterOfConfigStaleTimeoutMilisecs)
            .getCountersPage(driver)
            .sleep(quaterOfConfigStaleTimeoutMilisecs)
            .closeCountersPage(driver)
            // change config and set statsReportingInterval = 82 secs
            .then(function () {
                request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds82);

                return driver .sleep(quaterOfConfigStaleTimeoutMilisecs)
                    .waitForResponse(driver)

                    //turn on the internet
                    .toggleNetwork(driver)
                    // wait for config refresh interval to load new config
                    .sleep(quaterOfConfigStaleTimeoutMilisecs)
                    .getCountersPage(driver)
                    .sleep(quaterOfConfigStaleTimeoutMilisecs)
                    .closeCountersPage(driver)
                    .getCountersPage(driver)
                    .sleep(quaterOfConfigStaleTimeoutMilisecs)
                    .closeCountersPage(driver)
                    .getCountersPage(driver)
                    .sleep(quaterOfConfigStaleTimeoutMilisecs)
                    .closeCountersPage(driver)
                    // check that stats_report_interval will be 82, so new config is loaded
                    .then(function () {
                        return driver
                            .getConfigurationPage(driver)
                            .getStatsReportingInterval(driver)
                            .then(function (statsReportingInterval) {
                                return statsReportingInterval.text().should.become(statsReportingIntervalSeconds82 + "");
                            }); 
                    });
            })
            
            
    });
});