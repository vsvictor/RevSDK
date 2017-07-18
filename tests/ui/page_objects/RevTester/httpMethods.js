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

var Methods = {
    dropdown: {
        methods: 'com.nuubit.tester:id/spMethod'
    },
    list: {
        methods: {
            GET: "//android.widget.TextView[@index='0']",
            POST: "//android.widget.TextView[@index='1']",
            PUT: "//android.widget.TextView[@index='2']",
            DELETE: "//android.widget.TextView[@index='3']",
            HEAD: "//android.widget.TextView[@index='4']",
            CONNECT: "//android.widget.TextView[@index='5']",
            OPTIONS: "//android.widget.TextView[@index='6']",
            TRACE: "//android.widget.TextView[@index='7']"
        }
    },

    setHttpMethodGET: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.GET)
            .click();
    },

    setHttpMethodPOST: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.POST)
            .click();
    },

    setHttpMethodPUT: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.PUT)
            .click();
    },

    setHttpMethodDELETE: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.DELETE)
            .click();
    },

    setHttpMethodCONNECT: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.CONNECT)
            .click();
    },

    setHttpMethodHEAD: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.HEAD)
            .click();
    },

    setHttpMethodOPTIONS: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.OPTIONS)
            .click();
    },

    setHttpMethodTRACE: function (driver) {
        return driver
            .elementById(Methods.dropdown.methods)
            .click()
            .elementByXPath(Methods.list.methods.TRACE)
            .click();
    }
};

module.exports = Methods;




