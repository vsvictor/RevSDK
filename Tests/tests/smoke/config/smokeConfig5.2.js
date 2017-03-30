"use strict";

require("./helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    actions = require("./helpers/actions"),
    serverConfigs = require('./helpers/appium-servers'),
    values = require("./helpers/values").values,
    logging = require("./helpers/logging"),
    apps = require("./helpers/apps"),
    caps = require("./helpers/caps"),
    Page = require("./../../../page_objects/RevDemo/mainNavigation"),
    ConfigurationPage = require("./../../../page_objects/RevDemo/configurationPage"),
    request = require("./helpers/requests");
wd.addPromiseChainMethod('swipe', actions.swipe);

describe("android simple", function () {
    this.timeout(300000);
    var driver = undefined;
    var portalAPIKey = values.portalAPIKey;
    var appId = values.appId;
    var accountId = values.accountId;
    var statsReportingIntervalSeconds84 = values.statsReportingIntervalSeconds84;
    var statsReportingIntervalSeconds60 = values.statsReportingIntervalSeconds60;
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
        setTimeout(function () {
            request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds84);
        }, configurationRefreshIntervalMiliSec);

        return driver
            .sleep(30000)
            .Page.clickMenuBtn()
            .sleep(30000)
            .Page.clickConfigViewBtn()
            .sleep(30000)
            .Page.clickMenuBtn()
            .sleep(30000)
            .Page.clickConfigViewBtn()
            .sleep(2000)
            .ConfigurationPage.getConfigList()
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds84.toString());
            });
    });
});

