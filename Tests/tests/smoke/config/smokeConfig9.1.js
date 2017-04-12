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
    Menu = require("./../../../page_objects/RevDemo/mainNavigation"),
    ConfigurationPage = require("./../../../page_objects/RevDemo/configurationPage"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('swipe', actions.swipe);

describe("android simple", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appId = config.get('appId');
    var accountId = config.get('accountId');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
    var statsReportingIntervalSeconds85 = config.get('statsReportingIntervalSeconds85');
    var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
    var serverConfig = serverConfigs.local;
    driver = wd.promiseChainRemote(serverConfig);
    logging.configure(driver);
    var desired = _.clone(caps.android19);
    desired.app = apps.androidApiDemos;

    before(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds85);
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .sleep(1000)
            .swipe({
                startX: 600, startY: 50,
                endX: 600, endY: 1000,
                duration: 600
            })
            .sleep(3000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 350
            })
            .sleep(4000)
            .swipe({
                startX: 800, startY: 150,
                endX: 800, endY: 150,
                duration: 350
            })
            .sleep(3000)
            .swipe({
                startX: 300, startY: 2000,
                endX: 300, endY: 50,
                duration: 1500
            })
            .sleep(4000)
            .quit();
    });


    it("should save config after reloading the App", function () {
        //turn off network
        //run app, wait for config
        //restart app
        //check that our config is still there
        //turn on network
        return driver
            .sleep(7000)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .sleep(1000)
            .swipe({
                startX: 600, startY: 50,
                endX: 600, endY: 1000,
                duration: 600
            })
            .sleep(3000)
            .swipe({
                startX: 100, startY: 275,
                endX: 100, endY: 275,
                duration: 350
            })
            .sleep(4000)
            .swipe({
                startX: 800, startY: 150,
                endX: 800, endY: 150,
                duration: 350
            })
            .sleep(3000)
            .swipe({
                startX: 300, startY: 2000,
                endX: 300, endY: 50,
                duration: 1500
            })
            .sleep(4000)
            .quit()
            .init(desired)
            .setImplicitWaitTimeout(6000)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds85 + "");
            })
    });
});

