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

var Config = {
    list: {
        config: '//android.widget.TextView'
    },

    getConfigurationList: function (driver) {
        return driver
            .elementsByXPath(Config.list.config);
    },

    getStatsReportingInterval: function (driver) {
        return driver
            .elementsByXPath(Config.list.config)
            .at(2);
    },

    getOperationMode: function (driver) {
        return driver
            .elementsByXPath(Config.list.config)
            .at(28);
    },

    getDomainsBlackList: function (driver) {
        return driver
            .elementsByXPath(Config.list.config)
            .at(42);
    },

    getDomainsWhiteList: function (driver) {
        return driver
            .elementsByXPath(Config.list.config)
            .at(26);
    },

    getDomainsProvisionedList: function (driver) {
        return driver
            .elementsByXPath(Config.list.config)
            .at(58);
    },

    getInitialTransportProtocol: function (driver) {
        return driver
            .elementsByXPath(Config.list.config)
            .at(48);
    },

    getConfigVariables: function (driver) {
        var configVariables = [];
        return driver
            .elementsByXPath(Config.list.config)
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
            .elementsByXPath(Config.list.config)
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
    }
};

module.exports = Config;




