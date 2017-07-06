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

var wd = require('wd'),
    actions = require("./../../helpers/actions"),
    App = require("./mainPage");

var config = require("config");

wd.addPromiseChainMethod('closeSettings', actions.closeSettings);
wd.addPromiseChainMethod('openSettings', actions.openSettings);
wd.addPromiseChainMethod('getInputUrl', App.getInputUrl);
wd.addPromiseChainMethod('clickSendBtn', App.clickSendBtn);

var Functions =  {
    button: {
        networkImage: "//android.widget.ImageView[@index='0']"
    },
    checkBox: {
        switchNetwork: 'android.widget.Switch'
    },

    // Function toogleNetwork opens Android settings, switches network status on/off, closes Android settings
    toggleNetwork: function (driver) {
        return driver
            .openSettings()
            .elementByXPath(Functions.button.networkImage)
            .click()
            .elementByClassName(Functions.checkBox.switchNetwork)
            .click()
            .closeSettings();
    },

    // Function sendRequestOnURL sets URL value in input and clicks send button
    sendRequestOnURL: function (driver, url) {
        return driver
            .getInputUrl(driver)
            .sendKeys(url)
            .clickSendBtn(driver);
    }



};

module.exports = Functions;
