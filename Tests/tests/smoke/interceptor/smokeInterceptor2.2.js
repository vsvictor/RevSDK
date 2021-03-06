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
wd.addPromiseChainMethod('scrollDown', actions.scrollDown);

describe("Smoke Interceptor", function () {
    describe("Operation Modes: transfer_only", function () {
        var describeTimeout = config.get('describeTimeout');
        this.timeout(describeTimeout);
        var driverRevTester = undefined;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        var portalAPIKey = config.get('portalAPIKey');
        var appIdTester = config.get('appIdTester');
        var accountId = config.get('accountId');
        var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
        var domainsWhiteList = config.get('domainsWhiteList');
        var domainsBlackList = undefined;
        var domainsProvisionedList = undefined;

        before(function () {
            request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
                domainsWhiteList, domainsBlackList = [], domainsProvisionedList = []);
            var serverConfig = serverConfigs.local;
            driverRevTester = wd.promiseChainRemote(serverConfig);
            logging.configure(driverRevTester);
            var desired = _.clone(caps.android19);
            desired.app = apps.androidTester;
            var implicitWaitTimeout = config.get('implicitWaitTimeout');
            return driverRevTester
                .sleep(5000)
                .init(desired)
                .setImplicitWaitTimeout(implicitWaitTimeout);
        });

        after(function () {
            request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
            return driverRevTester
                .quit();
        });

       it("should check that domain from 'WHITE' list will return Rev Headers", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_only)
                .click()
                .elementById(App.input.url)
                .sendKeys(domainsWhiteList[1])
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementByClassName(App.menuBtn.button)
                .click()
                .elementByXPath(App.menuOptions.statsView)
                .click()
                .sleep(2000)
                .scrollDown()
                .scrollDown()
                .scrollDown()
                .scrollDown()
                .sleep(5000)
                .elementsByXPath(App.list.stats)
                .then(function (els) {
                    return els[21].text();
                })
                .then(function (el21) {
                    var revRequestsValue = Number(el21);
                    return revRequestsValue.should.equal(0);
                });
        });
    });
});

