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
    var driverRevTester = undefined;
    var driverRevDemo = undefined;
    var portalAPIKey = values.portalAPIKey;
    var appId = values.appId;
    var accountId = values.accountId;
    var statsReportingIntervalSeconds60 = values.statsReportingIntervalSeconds60;
    var configurationRefreshIntervalMiliSec = 60000;

    before(function () {
        var serverConfig = serverConfigs.local;
        driverRevTester = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevTester);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        //turn off the network using RevTester and then quit RevTester
        return driverRevTester
            .init(desired)
            .setImplicitWaitTimeout(6000)
            .sleep(1000)
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
            .quit();
    });

    after(function () {
        request.putConfig(appId, portalAPIKey, accountId, statsReportingIntervalSeconds60);
        //turn on network and quit RevDemo
        return driverRevDemo
            .sleep(1000)
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
            .quit();
    });


    it("should find an element", function () {

        var serverConfig = serverConfigs.local;
        driverRevDemo = wd.promiseChainRemote(serverConfig);
        logging.configure(driverRevDemo);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidApiDemos;

        return driverRevDemo
            .init(desired)
            .setImplicitWaitTimeout(6000)
            .sleep(1000)
            .elementByClassName('android.widget.ImageButton')
            .click()
            .sleep(1000)
            .elementByXPath('//android.widget.CheckedTextView[@text=\'Configuration view\']')
            .click()
            .sleep(1000)
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[1].text().should.become("stats_reporting_interval_sec");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[3].text().should.become("stats_reporting_level");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[5].text().should.become("edge_failures_failover_threshold_percent");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[7].text().should.become("edge_quic_udp_port");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[9].text().should.become("edge_data_receive_timeout_sec");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[11].text().should.become("app_name");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[13].text().should.become("internal_domains_black_list");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[15].text().should.become("a_b_testing_origin_offload_ratio");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[17].text().should.become("sdk_release_version");
            })
            .elementsByXPath('//android.widget.TextView')
            .then(function (els) {
                return els[19].text().should.become("transport_monitoring_url");
            });
    });
});

