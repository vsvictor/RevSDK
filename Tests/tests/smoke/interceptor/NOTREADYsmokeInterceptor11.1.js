"use strict";

require("./helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    actions = require("./helpers/actions"),
    serverConfigs = require('./helpers/appium-servers'),
    _p = require('./helpers/promise-utils'),
    Q = require('q');
wd.addPromiseChainMethod('swipe', actions.swipe);

var request = require("request");

describe("android simple", function () {
    this.timeout(300000);
    var driver;
    var allPassed = true;
    var nameOfTheNewApp = "QAAndroid" + new Date();
    var appIDFromResponseBody;
    var portalAPIKey = "X-API-KEY 465618f8-dd75-4165-bfa2-70b10d955ad4";
    var appId = "58d535b8519150d22ab102b4";
    var accountId = "585296f11ea9bb3c3f90e05b";
    var statsReportingIntervalSeconds = 77;

    before(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        require("./helpers/logging").configure(driver);

        var desired = _.clone(require("./helpers/caps").android19);
        desired.app = require("./helpers/apps").androidTester;
        return driver
            .init(desired)
            .setImplicitWaitTimeout(5000);
    });

    after(function () {
        return driver
            .quit();
    });

    afterEach(function () {
        allPassed = allPassed && this.currentTest.state === 'passed';
    });

    it("should find an element", function () {

    });

});