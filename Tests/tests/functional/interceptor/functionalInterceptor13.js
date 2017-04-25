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
    App = require("./../../../page_objects/RevTester/mainPage");

describe("Functional Interceptor", function () {
    describe("check that response bodies with and without SDK are identical.", function () {
        var describeTimeout = config.get('describeTimeout');
        this.timeout(describeTimeout);
        var driverRevTester = undefined;
        var portalAPIKey = config.get('portalAPIKey');
        var appIdTester = config.get('appIdTester');
        var accountId = config.get('accountId');
        var httpsWebsite = config.get('httpsWebsite');

        before(function () {
            var serverConfig = serverConfigs.local;
            driverRevTester = wd.promiseChainRemote(serverConfig);
            logging.configure(driverRevTester);
            var desired = _.clone(caps.android19);
            desired.app = apps.androidTester;
            var implicitWaitTimeout = config.get('implicitWaitTimeout');
            return driverRevTester
                .init(desired)
                .setImplicitWaitTimeout(implicitWaitTimeout);
        });

        after(function () {
            return driverRevTester
                .quit();
        });

        it("should check HTTPS Method GET", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.GET)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.GET)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method POST", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.POST)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.POST)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method PUT", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.PUT)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.PUT)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method DELETE", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.DELETE)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.DELETE)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method HEAD", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.HEAD)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.HEAD)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method CONNECT", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.CONNECT)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.CONNECT)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method OPTIONS", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.OPTIONS)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.OPTIONS)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });

        it("should check HTTPS Method TRACE", function () {
            return driverRevTester
                .elementById(App.dropdown.operationModes)
                .click()
                .elementByXPath(App.list.operationModes.transfer_and_report)
                .click()
                .elementById(App.dropdown.methods)
                .click()
                .elementByXPath(App.list.methods.TRACE)
                .click()
                .elementById(App.input.url)
                .sendKeys(httpsWebsite)
                .elementById(App.button.send)
                .click()
                .sleep(5000)
                .elementById(App.output.response)
                .then(function (els) {
                    return els.text();
                })
                .then(function (response) {
                    return driverRevTester
                        .elementById(App.dropdown.operationModes)
                        .click()
                        .elementByXPath(App.list.operationModes.off)
                        .click()
                        .elementById(App.dropdown.methods)
                        .click()
                        .elementByXPath(App.list.methods.TRACE)
                        .click()
                        .elementById(App.input.url)
                        .sendKeys(httpsWebsite)
                        .elementById(App.button.send)
                        .click()
                        .sleep(5000)
                        .elementById(App.output.response)
                        .then(function (els) {
                            return els.text().should.become(response);
                        });
                });
        });
    });
});

