"use strict";

require("./../../../helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    actions = require("./../../../helpers/actions"),
    serverConfigs = require('./../../../helpers/appium-servers'),
    values = require("./../../../helpers/values").values,
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    Page = require("./../../../page_objects/RevDemo/mainNavigation"),
    ConfigurationPage = require("./../../../page_objects/RevDemo/configurationPage"),
    request = require("./../../../helpers/requests");
    wd.addPromiseChainMethod('swipe', actions.swipe);

describe("android simple", function () {
    this.timeout(30000);
    var driver = undefined;
    var portalAPIKey = values.portalAPIKey;
    var appId = values.appId;
    var accountId = values.accountId;
    var statsReportingIntervalSeconds82 = values.statsReportingIntervalSeconds82;
    var statsReportingIntervalSeconds60 = values.statsReportingIntervalSeconds60;

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

    it("should find an element", function () {
        return driver
            .sleep(2000)
            .Page.clickMenuBtn()
            .sleep(2000)
            .Page.clickConfigViewBtn()
            .sleep(2000)
            .ConfigurationPage.getConfigList()
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds82 + "");
            });
    });
});
