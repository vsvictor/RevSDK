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

require("./helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    actions = require("./helpers/actions"),
    serverConfigs = require('./helpers/appium-servers'),
    configDefaultValues = require("./../../../config/default").values,
    logging = require("./helpers/logging"),
    apps = require("./helpers/apps"),
    caps = require("./helpers/caps"),
    request = require("./helpers/requests");
wd.addPromiseChainMethod('swipe', actions.swipe);

describe("android simple", function () {
    this.timeout(configDefaultValues.describeTimeout);
    var driver = undefined;
    var portalAPIKey = configDefaultValues.portalAPIKey;
    var appId = configDefaultValues.appId;
    var accountId = configDefaultValues.accountId;
    var statsReportingIntervalSeconds60 = configDefaultValues.statsReportingIntervalSeconds60;
    var statsReportingIntervalSeconds85 = configDefaultValues.statsReportingIntervalSeconds85;
    var configurationRefreshIntervalMiliSec = 60000;

    before(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidApiDemos;
        return driver
            .init(desired)
            .setImplicitWaitTimeout(6000);
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });


    it("should find an element", function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds85);
        //go to config after 1 minute, when our config loaded
        //check that config loaded
        //turn off network
        //restart app
        //check that our config is still there
        return driver
            .sleep(30000)
            .elementByClassName('android.widget.ImageButton')
            .click()
            .sleep(30000)
            .elementByXPath('//android.widget.CheckedTextView[@text=\'Configuration view\']')
            .click()
            .sleep(1000)
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds85.toString());
            })
            .swipe({
                startX: 300, startY: 50,
                endX: 300, endY: 500,
                duration: 500
            })
            .sleep(1000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 300
            })
            .sleep(1000)
            .swipe({
                startX: 1300, startY: 150,
                endX: 1300, endY: 150,
                duration: 400
            })
            .sleep(1500)
            .swipe({
                startX: 300, startY: 1500,
                endX: 300, endY: 50,
                duration: 500
            })
            .resetApp()
            .sleep(1000)
            .elementByClassName('android.widget.ImageButton')
            .click()
            .sleep(1000)
            .elementByXPath('//android.widget.CheckedTextView[@text=\'Configuration view\']')
            .click()
            .sleep(1000)
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds85.toString());
            })
            .swipe({
                startX: 300, startY: 50,
                endX: 300, endY: 500,
                duration: 500
            })
            .sleep(1000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 300
            })
            .sleep(1000)
            .swipe({
                startX: 1300, startY: 150,
                endX: 1300, endY: 150,
                duration: 400
            })
            .sleep(1000);
    });
});

