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

var wd = require("wd");
var Waits = require("./waits");

wd.addPromiseChainMethod('waitForResponse', Waits.waitForResponse);
wd.addPromiseChainMethod('waitForResponseStatusCode', Waits.waitForResponseStatusCode);

var Fields = {
    output: {
        response: 'com.nuubit.tester:id/tvMain',
        responseHeaders: 'com.nuubit.tester:id/tvHeaders',
        responseStatusCode: 'com.nuubit.tester:id/tvHTTPCode'
    },

    // Function getResponseHeadersFieldValue waits till we get response and returns value of the Response Headers Field
    getResponseHeadersFieldValue: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementById(Fields.output.responseHeaders);
    },

    // Function getResponseBodyFieldValue waits till we get response and returns value of the Response Body Field
    getResponseBodyFieldValue: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementById(Fields.output.response);
    },

    // Function getResponseBodyFieldValue waits till we get response and returns value of the Response Status Code  Field
    getResponseStatusCodeValue: function (driver, code) {
        return driver
            .waitForResponse(driver)
            .elementById(Fields.output.responseStatusCode);
    }
};

module.exports = Fields;




