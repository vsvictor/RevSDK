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

var wd = require("wd"),
    config = require("config"),
    actions = require("./../../helpers/actions"),
    Waits = require("./../../page_objects/RevTester/waits"),
    Functions = require("./functions");

var defaultStatsVars = config.get('defaultStatsVars');

wd.addPromiseChainMethod('scrollDown', actions.scrollDown);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);

var Counters = {
    list: {
        drawer: '//android.widget.TextView'
    },


    getOriginRequests: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                return countersList[47].text().then(function (value) {
                    return value === 'originRequests' ? countersList[48].text() : countersList[47].text();
                });
            });
    },

    getRevRequests: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                return countersList[45].text().then(function (value) {
                    return value === 'revRequests' ? countersList[46].text() : countersList[45].text();
                });
            });
    },

    getCounterRequestCount: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                return countersList[62].text().then(function (value) {
                    return value === 'Request count' ? countersList[63].text() : countersList[62].text();
                });
            });
    },

    getTotalStatsRequestUploaded: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                return countersList[58].text();
            });
    },

    // Function getCounterTotalRequestsStandard scrolls down the counters and returns value of the totalRequestsStandard
    getCounterTotalRequestsStandard: function (driver) {
        var totalRequestsStandard = undefined;
        return driver
            .waitForResponse(driver)
            .scrollDown()
            .scrollDown()
            .scrollDown()
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                totalRequestsStandard = countersList[52].text();
                return totalRequestsStandard;
            });
    }
};

module.exports = Counters;




