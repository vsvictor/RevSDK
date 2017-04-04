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
    request = require("./helpers/requests");
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
            .elementByClassName('android.widget.ImageButton')
            .click()
            .sleep(2000)
            .elementByXPath('//android.widget.CheckedTextView[@text=\'Configuration view\']')
            .click()
            .sleep(2000)
            .elementByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds82 + "");
            });
    });
});
