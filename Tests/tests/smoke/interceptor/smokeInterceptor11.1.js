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
    configDefaultValues = require("./../../../config/default").values,
    logging = require("./../../../helpers/logging"),
    apps = require("./../../../helpers/apps"),
    caps = require("./../../../helpers/caps"),
    Menu = require("./../../../page_objects/RevDemo/mainNavigation"),
    Configuration = require("./../../../page_objects/RevTester/mainPage"),
    requestAppium = require("request"),
    request = require("./../../../helpers/requests"),
    responsesFromServer = require("./../../../helpers/appiumHttpResponses").responses;


describe("Smoke Interceptor", function () {
    describe("check that response bodies with and without SDK are identical.", function () {
        this.timeout(configDefaultValues.describeTimeout);
        var driverRevTester = undefined;
        var driverRevDemo = undefined;
        var portalAPIKey = configDefaultValues.portalAPIKey;
        var appId = configDefaultValues.appId;
        var accountId = configDefaultValues.accountId;
        var responseFromAppium = responsesFromServer.responseFromAppium;
        var responseFromAppiumMethodPOST = responsesFromServer.methodPOST;

        before(function () {
            var serverConfig = serverConfigs.local;
            driverRevTester = wd.promiseChainRemote(serverConfig);
            logging.configure(driverRevTester);
            var desired = _.clone(caps.android19);
            desired.app = apps.androidTester;
            return driverRevTester
                .init(desired)
                .setImplicitWaitTimeout(6000)
        });

        after(function () {
            return driverRevTester
                .quit();
        });

        it("should check HTTP Method GET", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(0) //GET
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response) // response body
                .sleep(2000)
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

        it("should check HTTP Method POST", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(1) //POST
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

        it("should check HTTP Method PUT", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(2) //PUT
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });


        it("should check HTTP Method DELETE", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(3) //DELETE
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

        it("should check HTTP Method HEAD", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(4) //HEAD
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

        it("should check HTTP Method CONNECT", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(5) //CONNECT
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

        it("should check HTTP Method OPTIONS", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(6) //OPTIONS
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

        it("should check HTTP Method TRACE", function () {
            return driverRevTester
                .sleep(1000)
                .elementById(Configuration.dropdown.methods)  // methods d-down
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.methods)   // list of methods
                .at(7) //TRACE
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000)
                .elementById(Configuration.dropdown.operationModes)  // modes operation
                .click()
                .sleep(5000)
                .elementsByClassName(Configuration.list.operationModes)   // list of methods
                .at(3) // Off mode
                .click()
                .sleep(1000)
                .elementById(Configuration.input.url) // input url
                .sendKeys("appium.io")
                .elementById(Configuration.button.send)  // Go\Run\Send btn
                .click()
                .sleep(2000)
                .elementById(Configuration.output.response)  // response body
                .then(function (els) {
                    return els.text().should.become(responseFromAppium);
                })
                .sleep(3000);
        });

    });
});

