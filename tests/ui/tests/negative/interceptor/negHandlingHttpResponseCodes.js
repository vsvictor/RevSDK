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
    httpMethods = require("./../../../page_objects/RevTester/httpMethods"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    Functions = require("./../../../page_objects/RevTester/functions");

wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('setHttpMethodPUT', httpMethods.setHttpMethodPUT);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseStatusCodeValue', httpFields.getResponseStatusCodeValue);

describe("Negative: interceptor. Handling http responses", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var httpsWebsite = config.get('httpsWebsite');
    var successHttpStatusCode = config.get('successHttpStatusCode');
    var httpBinWebsiteStatuses = config.get('httpBinWebsiteStatuses');
    var httpBinStatuses = config.get('httpBinStatuses');

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

    it("should check that SDK handles response status code 400", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[400])
            .getResponseStatusCodeValue(driver)
            .then(function (statusCode) {
                return statusCode.text().should.become(httpBinStatuses[400]);
            });
    });

    it("should check that SDK handles response status code 402", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[402])
            .getResponseStatusCodeValue(driver)
            .then(function (statusCode) {
                return statusCode.text().should.become(httpBinStatuses[402]);
            });
    });

    it("should check that SDK handles response status code 404", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[404])
            .getResponseStatusCodeValue(driver)
            .then(function (statusCode) {
                return statusCode.text().should.become(httpBinStatuses[404]);
            });
    });

    it("should check that SDK handles response status code 300", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[300])
            .getResponseStatusCodeValue(driver)
            .then(function (statusCode) {
                return statusCode.text().should.become(httpBinStatuses[300]);
            });
    });

    it("should check that SDK handles response status code 301", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[301])
            .getResponseStatusCodeValue(driver)
            .then(function (statusCode) {
                return statusCode.text().should.become(httpBinStatuses[301]);
            });
    });

    it("should check that SDK handles response status code 302", function () {
        return driver
            .setModeTransferOnly(driver)
            .sendRequestOnURL(driver, httpBinWebsiteStatuses[302])
            .getResponseStatusCodeValue(driver)
            .then(function (statusCode) {
                return statusCode.text().should.become(httpBinStatuses[302]);
            });
    });
});


















