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
    config = require("config"),
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    Functions = require("./../../../page_objects/RevTester/functions"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    request = require("./../../../helpers/requests");

wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseBodyFieldValue', httpFields.getResponseBodyFieldValue);

describe("Functional Interceptor", function () {
    describe("Different symbols in URL (Encoding)", function () {
        var describeTimeout = config.get('describeTimeout');
        this.timeout(describeTimeout);
        var driver = undefined;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        var portalAPIKey = config.get('portalAPIKey');
        var appIdTester = config.get('appIdTester');
        var accountId = config.get('accountId');
        var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');
        var encoding = config.get('encoding');

        before(function () {
            request.putConfig(appIdTester, portalAPIKey, accountId, statsReportingIntervalSeconds60);

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

        it("should check that SDK handles German symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .sendRequestOnURL(driver, encoding.german.site)
                .getResponseBodyFieldValue(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.german.value)
                })
        });

        it("should check that SDK handles Russian symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .sendRequestOnURL(driver, encoding.russian.site)
                .getResponseBodyFieldValue(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.russian.value)
                })
        });

        it("should check that SDK handles Turkish symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .sendRequestOnURL(driver, encoding.turkish.site)
                .getResponseBodyFieldValue(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.turkish.value)
                })
        });

        it("should check that SDK handles Chinese symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .sendRequestOnURL(driver, encoding.chinese.site)
                .getResponseBodyFieldValue(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.chinese.value)
                })
        });

        it("should check that SDK handles Arabic symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .sendRequestOnURL(driver, encoding.arabic.site)
                .getResponseBodyFieldValue(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.arabic.value)
                })
        });

        it("should check that SDK handles German symbols encoding", function () {
            return driver
                .setModeTransferOnly(driver)
                .sendRequestOnURL(driver, encoding.german.site)
                .getResponseBodyFieldValue(driver)
                .then(function (body) {
                    return body.text().should.eventually.include(encoding.german.value)
                })
        });
    });
});

