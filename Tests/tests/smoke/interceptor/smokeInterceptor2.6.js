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
    Configuration = require("./../../../page_objects/RevTester/mainPage"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('swipe', actions.swipe);

describe("Smoke Interceptor", function () {
    describe("Operation Modes", function () {
        var describeTimeout = config.get('describeTimeout');
        this.timeout(describeTimeout);
        var driverRevTester = undefined;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        var portalAPIKey = config.get('portalAPIKey');
        var appIdTester = config.get('appIdTester');
        var accountId = config.get('accountId');
        var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
        var domainsWhiteList = config.get('domainsWhiteList');
        var domainsBlackList = config.get('domainsBlackList');
        var domainsProvisionedList = config.get('domainsProvisionedList');

        before(function () {
            request.putConfigWithDomainsLists(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60,
                domainsWhiteList = [], domainsBlackList = [], domainsProvisionedList);
            var serverConfig = serverConfigs.local;
            driverRevTester = wd.promiseChainRemote(serverConfig);
            logging.configure(driverRevTester);
            var desired = _.clone(caps.android19);
            desired.app = apps.androidTester;
            var implicitWaitTimeout = config.get('implicitWaitTimeout');
            return driverRevTester
                .init(desired)
                .setImplicitWaitTimeout(implicitWaitTimeout);
        });

        after(function () {
            request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);
            return driverRevTester
                .quit();
        });

        it("should check that domain from 'PROVISIONED' list won't return SDK key", function () {
            return driverRevTester
                .sleep(5000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(3000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(1) // Transfer mode
                .click()
                .sleep(3000)
                .elementById(Configuration.input.url) // input url
                .sendKeys(domainsProvisionedList[0])
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(5000)
                .elementById(Configuration.output.responseHeaders) // response header
                .then(function (els) {
                    return els.text().should.not.eventually.include("x-rev-host")
                })
        });
    });
});

