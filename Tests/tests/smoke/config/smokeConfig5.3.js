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
    config = require("config"),
    actions = require("./../../../helpers/actions"),
    serverConfigs = require('./../../../helpers/appium-servers'),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    App = require("./../../../page_objects/RevTester/mainPage"),
    request = require("./../../../helpers/requests");

describe("Smoke Configuration", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds87 = config.get('statsReportingIntervalSeconds87');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');

    before(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("should check that config reloads after config_refresh interval*3 secs", function () {
        var halfConfigRefreshInterval = configurationRefreshIntervalMilliSec / 2;
        //change config on API after two minutes
        setTimeout(function () {
            request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds87);
        }, configurationRefreshIntervalMilliSec * 2);
        //check that sdk will load new config after config_refresh interval*2 secs
        return driver
            .sleep(halfConfigRefreshInterval)
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(halfConfigRefreshInterval)
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .sleep(halfConfigRefreshInterval)
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(halfConfigRefreshInterval)
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .sleep(halfConfigRefreshInterval)
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(halfConfigRefreshInterval + 3000)
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds87.toString());
            });
    });
});

