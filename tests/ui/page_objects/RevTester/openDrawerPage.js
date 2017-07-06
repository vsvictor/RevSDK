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
    actions = require("./../../helpers/actions"),
    Waits = require("./../../page_objects/RevTester/waits");

wd.addPromiseChainMethod('scrollDown', actions.scrollDown);
wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);

var Counters = {
    list: {
        drawer: '//android.widget.TextView'
    },

    // Function getCounterRevRequests waits till we get list and returns value of the revRequestsCounter
    getCounterRevRequests: function (driver) {
        var revRequestsCounter = undefined;
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                revRequestsCounter = countersList[45].text();
                return revRequestsCounter;
            });
    },

    getCounterRequestCount: function (driver, position) {
        var requestCount = undefined;
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Counters.list.drawer)
            .then(function (countersList) {
                requestCount = countersList[position || 62].text();
               return requestCount;
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
                totalRequestsStandard = countersList[54].text();
                return totalRequestsStandard;
            });
    }
};

module.exports = Counters;




