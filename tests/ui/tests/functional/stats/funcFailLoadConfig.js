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
    TouchAction = wd.TouchAction,
    Modes = require("./../../../page_objects/RevTester/operationModes"),
    httpMethods = require("./../../../page_objects/RevTester/httpMethods"),
    httpFields = require("./../../../page_objects/RevTester/httpFields"),
    Functions = require("./../../../page_objects/RevTester/functions");

var request = require("request");

wd.addPromiseChainMethod('toggleNetwork', Functions.toggleNetwork);
wd.addPromiseChainMethod('setModeTransferOnly', Modes.setModeTransferOnly);
wd.addPromiseChainMethod('setHttpMethodPUT', httpMethods.setHttpMethodPUT);
wd.addPromiseChainMethod('sendRequestOnURL', Functions.sendRequestOnURL);
wd.addPromiseChainMethod('getResponseStatusCodeValue', httpFields.getResponseStatusCodeValue);

describe("Functional: ", function () {
    var describeTimeout = config.get('describeTimeout');
    this.timeout(describeTimeout);
    var driver = undefined;
    var implicitWaitTimeout = config.get('implicitWaitTimeout');
    var httpsWebsite = config.get('httpsWebsite');
    var successHttpStatusCode = config.get('successHttpStatusCode');
    var accountId = config.get('accountId');
    var portalAPIKey = config.get('portalAPIKey');
    var statsReportingIntervalSeconds60 = config.get('statsReportingIntervalSeconds60');


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

    // TODO: in progress (need fix bug in app)
    xit("Load config.JSON. Then fail to load new config.JSON"+
        " after every config_refresh_interval_sec.", function () {
        return driver
            // .elementById('android:id/statusBarBackground')
            // .then(function(element){
            //     return element.tap();
            // })
            // .then(function () {
            //     var action = new TouchAction(driver);
            //     action
            //         .press({x:10, y:10})
            //         .moveTo({x: 10, y: 50})
            //         .release();
            //
            //     return action.perform();
            // })
            // .elementByXPath("//android.widget.Button[@index='0']").click()
            // .elementById('android:id/toggle').click().sleep(1000)
            // .elementById('android:id/button1').click()
            // .elementById('com.android.systemui:id/expand_indicator').click().sleep(1000)
            // .elementById('com.android.systemui:id/no_notifications').click();
            .toggleNetwork(driver);


                //return statusCode.text().should.become(""+successHttpStatusCode)
            });



    });



















