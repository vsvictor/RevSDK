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

var App = {
    menuBtn: {
        button: 'android.widget.ImageView'
    },
    menuOptions: {
        main: '//android.widget.TextView[@text=\'Main\']',
        configurationView: '//android.widget.TextView[@text=\'Configuration view\']',
        statsView: '//android.widget.TextView[@text=\'Statistic view\']',
        openDrawer: '//android.widget.TextView[@text=\'Open drawer\']'
    },
    input: {
        url: 'com.nuubit.tester:id/tlQuery'
    },
    button: {
        send: 'com.nuubit.tester:id/rlRun',
        fetchConfig: 'com.nuubit.tester:id/bUpdateConfig',
        sendStats: 'com.nuubit.tester:id/bSendReports'
    },
    layoutCountersPage: 'com.nuubit.tester:id/action_bar_root',

    getInputUrl: function (driver) {
        return driver
            .elementById(App.input.url);
    },

    clickSendBtn: function (driver) {
        return driver
            .elementById(App.button.send)
            .click();
    },

    clickSendStatsBtn: function (driver) {
        return driver
            .elementById(App.button.sendStats)
            .click();
    },

    clickFetchConfigBtn: function (driver) {
        return driver
            .elementById(App.button.fetchConfig)
            .click();
    },

    getMainPage: function (driver) {
        return driver
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(2000)
            .elementByXPath(App.menuOptions.main)
            .click()
            .sleep(2000);
    },

    getConfigurationPage: function (driver) {
        return driver
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(2000)
            .elementByXPath(App.menuOptions.configurationView)
            .click()
            .sleep(2000);
    },

    getStatsPage: function (driver) {
        return driver
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(2000)
            .elementByXPath(App.menuOptions.statsView)
            .click()
            .sleep(2000);
    },

    getCountersPage: function (driver) {
        return driver
            .elementByClassName(App.menuBtn.button)
            .click()
            .sleep(2000)
            .elementByXPath(App.menuOptions.openDrawer)
            .click()
            .sleep(2000);
    },

    closeCountersPage: function (driver) {
        var TouchAction = wd.TouchAction;
        var action = new TouchAction(driver);
        return driver
            .elementById(App.layoutCountersPage)
            .then(function(){
                action
                    .press({x: 50, y: 200})
                    .release();
                return action.perform();
            })
    },



    clickMenuButton: function (driver) {
        return driver
            .elementByClassName(App.menuBtn.button)
            .click();
    },

    clickConfigViewButton: function (driver) {
        return driver
            .elementByXPath(App.menuOptions.configurationView)
            .click();
    },

     clickStatsViewButton: function (driver) {
         return driver
             .elementByXPath(App.menuOptions.statsView)
             .click();
     }
};

module.exports = App;




