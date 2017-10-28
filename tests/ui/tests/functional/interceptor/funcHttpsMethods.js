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
    Waits = require("./../../../page_objects/RevTester/waits"),
    Functions = require("./../../../page_objects/RevTester/functions");

wd.addPromiseChainMethod('setModeTransferAndReport', Modes.setModeTransferAndReport);
wd.addPromiseChainMethod('setModeOff', Modes.setModeOff);
wd.addPromiseChainMethod('setHttpMethodGET', httpMethods.setHttpMethodGET);
wd.addPromiseChainMethod('setHttpMethodPOST', httpMethods.setHttpMethodPOST);
wd.addPromiseChainMethod('setHttpMethodPUT', httpMethods.setHttpMethodPUT);
wd.addPromiseChainMethod('setHttpMethodDELETE', httpMethods.setHttpMethodDELETE);
wd.addPromiseChainMethod('setHttpMethodHEAD', httpMethods.setHttpMethodHEAD);
wd.addPromiseChainMethod('setHttpMethodTRACE', httpMethods.setHttpMethodTRACE);
wd.addPromiseChainMethod('setHttpMethodOPTIONS', httpMethods.setHttpMethodOPTIONS);
wd.addPromiseChainMethod('setHttpMethodCONNECT', httpMethods.setHttpMethodCONNECT);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseBodyFieldValue', httpFields.getResponseBodyFieldValue);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);


describe("Functional: interceptor. check that response bodies with and without SDK are identical.", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var portalAPIKey = config.get('portalAPIKey');
    var appIdTester = config.get('appIdTester');
    var accountId = config.get('accountId');
    var websites = config.get('websites');

    beforeEach(function () {
        var serverConfig = serverConfigs.local;
        driver = wd.promiseChainRemote(serverConfig);
        logging.configure(driver);
        var desired = _.clone(caps.android19);
        desired.app = apps.androidTester;
        var implicitWaitTimeout = config.get('implicitWaitTimeout');
        return driver
            .waitForResponse(driver)
            .init(desired)
            .setImplicitWaitTimeout(implicitWaitTimeout);
    });

    afterEach(function () {
        return driver
            .waitForResponse(driver)
            .quit();
    });

     it("should check HTTP Method GET", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodGET(driver)
            .sendRequestOnURL(driver, websites.methods.GET.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) { 
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodGET(driver)
                    .sendRequestOnURL(driver, websites.methods.GET.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode.Connection === responseBodyTransferMode.Connection
                            && responseBodyOffMode.Host === responseBodyTransferMode.Host
                            && responseBodyOffMode['User-Agent'] === responseBodyTransferMode['User-Agent']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);

                    }).should.become(true);
            });
    });

    xit("should check HTTP Method POST", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodPOST(driver)
            .sendRequestOnURL(driver, websites.methods.POST.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodPOST(driver)
                    .sendRequestOnURL(driver, websites.methods.POST.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode.Connection === responseBodyTransferMode.Connection
                            && responseBodyOffMode.Host === responseBodyTransferMode.Host
                            && responseBodyOffMode['User-Agent'] === responseBodyTransferMode['User-Agent']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);

                    }).should.become(true);
            });
    });

    xit("should check HTTP Method PUT", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodPUT(driver)
            .sendRequestOnURL(driver, websites.methods.PUT.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodPUT(driver)
                    .sendRequestOnURL(driver, websites.methods.PUT.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode.Connection === responseBodyTransferMode.Connection
                            && responseBodyOffMode.Host === responseBodyTransferMode.Host
                            && responseBodyOffMode['User-Agent'] === responseBodyTransferMode['User-Agent']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);
                    }).should.become(true);
            });
    });

    xit("should check HTTP Method DELETE", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodDELETE(driver)
            .sendRequestOnURL(driver, websites.methods.DELETE.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodDELETE(driver)
                    .sendRequestOnURL(driver, websites.methods.DELETE.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode.Connection === responseBodyTransferMode.Connection
                            && responseBodyOffMode.Host === responseBodyTransferMode.Host
                            && responseBodyOffMode['User-Agent'] === responseBodyTransferMode['User-Agent']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);
                    }).should.become(true);
            });
    });

    xit("should check HTTP Method HEAD", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodHEAD(driver)
            .sendRequestOnURL(driver, websites.methods.HEAD.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodHEAD(driver)
                    .sendRequestOnURL(driver, websites.methods.HEAD.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode.Connection === responseBodyTransferMode.Connection
                            && responseBodyOffMode.Host === responseBodyTransferMode.Host
                            && responseBodyOffMode['User-Agent'] === responseBodyTransferMode['User-Agent']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);

                    }).should.become(true);
            });
    });

    xit("should check HTTP Method OPTIONS", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodOPTIONS(driver)
            .sendRequestOnURL(driver, websites.methods.OPTIONS.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodOPTIONS(driver)
                    .sendRequestOnURL(driver, websites.methods.OPTIONS.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode['Connection'] === responseBodyTransferMode['Connection']
                            && responseBodyOffMode['Host'] === responseBodyTransferMode['Host']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);

                    }).should.become(true);
            });
    });

    xit("should check HTTP Method TRACE", function () {
        return driver
            .setModeTransferAndReport(driver)
            .setHttpMethodTRACE(driver)
            .sendRequestOnURL(driver, websites.methods.TRACE.http)
            .getResponseBodyFieldValue(driver)
            .then(function (repsonseTransferMode) {
                return repsonseTransferMode.text();
            })
            .then(function (repsonseTransferModeText){
                var responseBodyTransferMode = JSON.parse(repsonseTransferModeText);
                responseBodyTransferMode = responseBodyTransferMode.headers;
                return responseBodyTransferMode;
            })
            .then(function (responseBodyTransferMode) {
                return driver
                    .setModeOff(driver)
                    .setHttpMethodTRACE(driver)
                    .sendRequestOnURL(driver, websites.methods.TRACE.http)
                    .getResponseBodyFieldValue(driver)
                    .then(function (repsonseOffMode) {
                       return repsonseOffMode.text();
                    })
                    .then(function (repsonseOffModeText){
                        var responseBodyOffMode = JSON.parse(repsonseOffModeText);
                        responseBodyOffMode = responseBodyOffMode.headers;

                        return (responseBodyOffMode['Connection'] === responseBodyTransferMode['Connection']
                            && responseBodyOffMode['Host'] === responseBodyTransferMode['Host']
                            && responseBodyOffMode['Accept-Encoding'] === responseBodyTransferMode['Accept-Encoding']);
                    }).should.become(true);
            });
    });
});


