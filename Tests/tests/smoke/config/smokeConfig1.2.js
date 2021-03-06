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
    App = require("./../../../page_objects/RevTester/mainPage");

describe("Smoke Configuration", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var defaultConfigVars = config.get('defaultConfigVars');


    before(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    after(function () {
        return driver
            .quit();
    });

    it("should load valid config parameters", function () {
      /*  var logcat = require('adbkit-logcat');
        var spawn = require('child_process').spawn;

// Retrieve a binary log stream
        var proc = spawn('adb', ['logcat', '-B']);

// Connect logcat to the stream
        var reader = logcat.readStream(proc.stdout);
        reader.on('entry', function(entry) {
            console.log(entry.message);
        });*/
        return driver
            .elementByClassName(App.menuBtn.button)
            .click()
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[1].text().should.become(defaultConfigVars.stats_reporting_interval_sec);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[3].text().should.become(defaultConfigVars.stats_reporting_level);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[5].text().should.become(defaultConfigVars.edge_failures_failover_threshold_percent);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[7].text().should.become(defaultConfigVars.edge_quic_udp_port);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[9].text().should.become(defaultConfigVars.edge_data_receive_timeout_sec);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[11].text().should.become(defaultConfigVars.app_name);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[13].text().should.become(defaultConfigVars.internal_domains_black_list);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[15].text().should.become(defaultConfigVars.a_b_testing_origin_offload_ratio);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[17].text().should.become(defaultConfigVars.sdk_release_version);
            })
            .elementsByXPath(App.list.config)
            .then(function (els) {
                return els[19].text().should.become(defaultConfigVars.transport_monitoring_url);
            });
    });
});
