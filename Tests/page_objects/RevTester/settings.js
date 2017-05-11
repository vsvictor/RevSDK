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
var wd = require('wd'),
    actions = require("./../../helpers/actions");

var config = require("config");
var waitForResponseSecs = config.get('waitForResponseSecs');
var configurationRefreshIntervalMilliSec = config.get('configurationRefreshIntervalMilliSec');
var halfConfigRefreshInterval = configurationRefreshIntervalMilliSec / 2;
var waitForListSecs = config.get('waitForListSecs');

var Settings =  {
    button: {
        networkImage: "//android.widget.ImageView[@index='0']"
    },
    checkBox: {
        switchNetwork: 'android.widget.Switch'
    },
    toggleNetwork: function (driver) {
        wd.addPromiseChainMethod('closeSettings', actions.closeSettings);
        wd.addPromiseChainMethod('openSettings', actions.openSettings);
        return driver
            .openSettings()
            .elementByXPath(Settings.button.networkImage)
            .click()
            .elementByClassName(Settings.checkBox.switchNetwork)
            .click()
            .closeSettings();
    },
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
            .sleep(waitForResponseSecs);
    }
};

module.exports = Settings;
