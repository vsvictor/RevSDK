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
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    Menu = require("./../../../page_objects/RevDemo/mainNavigation"),
    Configuration = require("./../../../page_objects/RevDemo/configurationPage");

describe("Smoke Configuration", function () {
    this.timeout(configDefaultValues.describeTimeout);
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

    it("should load valid config parameters", function () {
        return driver
            .sleep(1000)
            .elementByClassName(Menu.menuBtn.button.className)
            .click()
            .sleep(1000)
            .elementByXPath(Menu.menuOptions.configurationView.xpath)
            .click()
            .sleep(1000)
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[1].text().should.become("stats_reporting_interval_sec");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[3].text().should.become("stats_reporting_level");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[5].text().should.become("edge_failures_failover_threshold_percent");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[7].text().should.become("edge_quic_udp_port");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[9].text().should.become("edge_data_receive_timeout_sec");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[11].text().should.become("app_name");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[13].text().should.become("internal_domains_black_list");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[15].text().should.become("a_b_testing_origin_offload_ratio");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[17].text().should.become("sdk_release_version");
            })
            .elementsByXPath(Configuration.lists.config.xpath)
            .then(function (els) {
                return els[19].text().should.become("transport_monitoring_url");
            });
    });
});
