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

var App = {
    dropdown: {
        methods: 'com.nuubit.tester:id/spMethod',
        operationModes: 'com.nuubit.tester:id/spMode'
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
        },
        operationModes: {
            transfer_and_report: "//android.widget.TextView[@index='0']",
            transfer_only: "//android.widget.TextView[@index='1']",
            report_only: "//android.widget.TextView[@index='2']",
            off: "//android.widget.TextView[@index='3']"
        },
        config: '//android.widget.TextView',
        stats: '//android.widget.TextView'
    },
    input: {
        url: 'com.nuubit.tester:id/tlQuery'
    },
    button: {
        send: 'com.nuubit.tester:id/rlRun'
    },
    output: {
        response: 'com.nuubit.tester:id/tvMain',
        responseHeaders: 'com.nuubit.tester:id/tvHeaders'
    },
    menuBtn: {
        button: 'android.widget.ImageView'
    },
    menuOptions: {
        configurationView: '//android.widget.TextView[@text=\'Configuration view\']',
        statsView: '//android.widget.TextView[@text=\'Statistic view\']'
    },
    getConfigurationPage: function (driver) {
        return driver
            .elementByClassName(App.menuBtn.button)
            .click()
            .elementByXPath(App.menuOptions.configurationView)
            .click();
    },
    getConfigurationList: function (driver) {
        return driver
            .elementsByXPath(App.list.config);
    },
    getStatsReportingInterval: function (driver) {
        return driver
            .elementsByXPath(App.list.config)
            .at(2);
    },
    getOperationMode: function (driver) {
        return driver
            .elementsByXPath(App.list.config)
            .at(28);
    },
    getConfigVariables: function (driver) {
        var configVariables = [];
        return driver
            .elementsByXPath(App.list.config)
            .then(function (configList) {
                configVariables[0] = configList[1].text();
                configVariables[1] = configList[3].text();
                configVariables[2] = configList[5].text();
                configVariables[3] = configList[7].text();
                configVariables[4] = configList[9].text();
                configVariables[5] = configList[11].text();
                configVariables[6] = configList[13].text();
                configVariables[7] = configList[15].text();
                configVariables[8] = configList[17].text();
                configVariables[9] = configList[19].text();
                return configVariables;
            });
    },
    getConfigValues: function (driver) {
        var configValues = [];
        return driver
            .elementsByXPath(App.list.config)
            .then(function (configList) {
                configValues[0] = configList[2].text();
                configValues[1] = configList[4].text();
                configValues[2] = configList[6].text();
                configValues[3] = configList[8].text();
                configValues[4] = configList[10].text();
                configValues[5] = configList[12].text();
                configValues[6] = configList[14].text();
                configValues[7] = configList[16].text();
                configValues[8] = configList[18].text();
                configValues[9] = configList[20].text();
                return configValues;
            });
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
    },
    getStatsValueRequests: function (driver) {
        var actions = require("./../../helpers/actions");
        var wd = require("wd");
        wd.addPromiseChainMethod('scrollDown', actions.scrollDown);
        var revRequests = undefined;
        return driver
            .sleep(2000)
            .scrollDown()
            .scrollDown()
            .scrollDown()
            .scrollDown()
            .scrollDown()
            .scrollDown()
            .sleep(5000)
            .elementsByXPath(App.list.stats)
            .then(function (statsList) {
                revRequests = statsList[21].text();
                return revRequests;
            });
    },
    setModeReportOnly: function (driver) {
       return driver
            .elementById(App.dropdown.operationModes)
            .click()
            .elementByXPath(App.list.operationModes.report_only)
            .click();
    },
    setModeOff: function (driver) {
        return driver
            .elementById(App.dropdown.operationModes)
            .click()
            .elementByXPath(App.list.operationModes.off)
            .click();
    },
    setModeTransferOnly: function (driver) {
        return driver
            .elementById(App.dropdown.operationModes)
            .click()
            .elementByXPath(App.list.operationModes.transfer_only)
            .click();
    },
    setModeTransferAndReport: function (driver) {
        return driver
            .elementById(App.dropdown.operationModes)
            .click()
            .elementByXPath(App.list.operationModes.transfer_and_report)
            .click();
    },
    getInputUrl: function (driver) {
        return driver
            .elementById(App.input.url);
    },
    clickSendBtn: function (driver) {
        return driver
            .elementById(App.button.send)
            .click();
    },
    getResponseHeaders: function (driver) {
        return driver
            .elementById(App.output.responseHeaders);
    },
    getResponseBody: function (driver) {
        return driver
            .elementById(App.output.response);
    },
    setHttpMethodGET: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.GET)
            .click();
    },
    setHttpMethodPOST: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.POST)
            .click();
    },
    setHttpMethodPUT: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.PUT)
            .click();
    },
    setHttpMethodDELETE: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.DELETE)
            .click();
    },
    setHttpMethodCONNECT: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.CONNECT)
            .click();
    },
    setHttpMethodHEAD: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.HEAD)
            .click();
    },
    setHttpMethodOPTIONS: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.OPTIONS)
            .click();
    },
    setHttpMethodTRACE: function (driver) {
        return driver
            .elementById(App.dropdown.methods)
            .click()
            .elementByXPath(App.list.methods.TRACE)
            .click();
    }
};

module.exports = App;




