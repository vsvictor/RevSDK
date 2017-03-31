"use strict";

require("./helpers/setup");

var wd = require("wd"),
    _ = require('underscore'),
    actions = require("./helpers/actions"),
    serverConfigs = require('./helpers/appium-servers'),
    logging = require("./helpers/logging"),
    apps = require("./helpers/apps"),
    caps = require("./helpers/caps");

describe("android simple", function () {
    this.timeout(300000);
    var driver;

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
        return driver
            .quit();
    });

    it("should find an element", function () {
        return driver
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
