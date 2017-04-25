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
    request = require("./../../../helpers/requests");
wd.addPromiseChainMethod('scrollDown', actions.scrollDown);

describe("Smoke Stats", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driverRevTester = undefined;
    var serverConfig = serverConfigs.local;
    driverRevTester = wd.promiseChainRemote(serverConfig);
    logging.configure(driverRevTester);
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
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        return driverRevTester
            .quit();
    });

    it("should check that stats survive after app restart", function () {
        return driverRevTester
            .elementById(App.dropdown.operationModes)
            .click()
            .elementByXPath(App.list.operationModes.transfer_and_report)
            .click()
            .elementById(App.input.url)
            .sendKeys(httpWebsite)
            .elementById(App.button.send)
            .click()
            .sleep(5000)
            // RESTART app
            .quit()
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout)
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
                return revRequestsValue.should.be.above(0);
            });
    });
});




