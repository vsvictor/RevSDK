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
        desired.app = apps.androidApiDemos;
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
        setTimeout(function () {
            request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds87);
        }, configurationRefreshIntervalMilliSec * 2);

        return driver
            .sleep(configurationRefreshIntervalMilliSec / 2)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .sleep(configurationRefreshIntervalMilliSec / 2)
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .sleep(configurationRefreshIntervalMilliSec / 2)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .sleep(configurationRefreshIntervalMilliSec / 2)
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .sleep(configurationRefreshIntervalMilliSec / 2)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .sleep( ( configurationRefreshIntervalMilliSec / 2 ) + 3000)
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .elementsByXPath(ConfigurationPage.lists.config.xpath)
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds87.toString());
            });
    });
});

