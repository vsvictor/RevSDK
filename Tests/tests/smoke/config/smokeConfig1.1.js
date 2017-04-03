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
    configDefaultValues = require("./../../../config/default").values,
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    Menu = require("./../../../page_objects/RevDemo/mainNavigation"),
    Configuration = require("./../../../page_objects/RevDemo/configurationPage"),
    request = require("./../../../helpers/requests");

describe("Smoke Configuration", function () {
    this.timeout(configDefaultValues.describeTimeout);
    var driver = undefined;
    var portalAPIKey = configDefaultValues.portalAPIKey;
    var appId = configDefaultValues.appId;
    var accountId = configDefaultValues.accountId;
    var statsReportingIntervalSeconds82 = configDefaultValues.statsReportingIntervalSeconds82;
    var statsReportingIntervalSeconds60 = configDefaultValues.statsReportingIntervalSeconds60;

    before(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidApiDemos;
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds82);
        return driver
            .init(desired)
            .setImplicitWaitTimeout(6000);
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        return driver
            .quit();
    });

    it("should load config on first initialization", function () {
        return driver
            .sleep(2000)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .sleep(2000)
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .sleep(1000)
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds82 + "");
            });
    });
});

