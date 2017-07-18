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

var wd = require('wd');
var config = require("config");

var waitForResponseSecs = config.get('waitForResponseSecs');
var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
var halfConfigRefreshInterval = configurationRefreshIntervalMilliSec / 2;
var waitForListSecs = config.get('waitForListSecs');
var waitForResponseStatusCodeSecs = config.get('waitForResponseStatusCodeSecs');

var Waits =  {
    waitHalfConfigRefreshInterval: function (driver) {
        return driver
            .sleep(halfConfigRefreshInterval);
    },

    waitForResponse: function (driver) {
        return driver
            .sleep(waitForResponseSecs);
    },

    waitForList: function (driver) {
        return driver
            .sleep(waitForListSecs);
    },

    waitForResponseStatusCode: function (driver) {
        return driver
            .sleep(waitForResponseStatusCodeSecs);
    }
};

module.exports = Waits;
