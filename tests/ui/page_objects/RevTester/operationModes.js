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

var Modes = {
    dropdown: {
        operationModes: 'com.nuubit.tester:id/spMode'
    },
    list: {
        operationModes: {
            transfer_and_report: "//android.widget.TextView[@index='0']",
            transfer_only: "//android.widget.TextView[@index='1']",
            report_only: "//android.widget.TextView[@index='2']",
            off: "//android.widget.TextView[@index='3']"
        }
    },

    setModeReportOnly: function (driver) {
        return driver
            .elementById(Modes.dropdown.operationModes)
            .click()
            .elementByXPath(Modes.list.operationModes.report_only)
            .click();
    },

    setModeOff: function (driver) {
        return driver
            .elementById(Modes.dropdown.operationModes)
            .click()
            .elementByXPath(Modes.list.operationModes.off)
            .click();
    },

    setModeTransferOnly: function (driver) {
        return driver
            .elementById(Modes.dropdown.operationModes)
            .click()
            .elementByXPath(Modes.list.operationModes.transfer_only)
            .click();
    },

    setModeTransferAndReport: function (driver) {
        return driver
            .elementById(Modes.dropdown.operationModes)
            .click()
            .elementByXPath(Modes.list.operationModes.transfer_and_report)
            .click();
    }
};

module.exports = Modes;




