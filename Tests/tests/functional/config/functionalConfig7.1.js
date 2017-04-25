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
    request = require("./../../../helpers/requests");

describe("Functional Config", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driverRevTester = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var domainsBlackList = config.get('domainsBlackList');
    var domainsWhiteList = config.get('domainsWhiteList');
    var headerRev = config.get('headerRev');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var halfConfigurationRefreshInterval = configurationRefreshIntervalMilliSec / 2;

    before(function () {
        request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        var serverConfig = serverConfigs.local;
        driverRevTester = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevTester);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driverRevTester
            .sleep(10000)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        return driverRevTester
            .quit();
    });

   it("should check that SDK switches to report_only mode on the first initialization", function () {
        return driverRevTester
            .elementByClassName(App.menuBtn.button)
            .click()
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .elementsByXPath(App.list.config)
            .then(function (els) {
                for (var i = 0; i < els.length; i++)
                console.log(els[i].text());
                return els[2].text().should.become(statsReportingIntervalSeconds82 + "");
            });
    });
});


