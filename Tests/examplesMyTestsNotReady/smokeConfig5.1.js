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
    this.timeout(300000);
    var driver = undefined;
    var portalAPIKey = values.portalAPIKey;
    var appId = values.appId;
    var accountId = values.accountId;
    var statsReportingIntervalSeconds83 = values.statsReportingIntervalSeconds83;
    var statsReportingIntervalSeconds60 = values.statsReportingIntervalSeconds60;

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
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds83);
        return driver
            .sleep(30000)
            .elementByClassName('android.widget.ImageButton')
            .click()
            .sleep(30000)
            .elementByXPath('//android.widget.CheckedTextView[@text=\'Configuration view\']')
            .click()
            .sleep(3000)
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[2].text().should.become(statsReportingIntervalSeconds83.toString());
            });
    });
});
