"use strict";

var Functions = require("./functions");
var config = require('config');

var defaultStatsVars = config.get('defaultStatsVars');

var Stats = {
    list: {
        config: '//android.widget.TextView'
    },

    getStatsList: function (driver) {
        return driver
            .elementsByXPath(Stats.list.config);
    },

    getSdkKey: function (driver) {
        driver.sleep(2000)
        return Functions
            .getValueFromList(driver, defaultStatsVars.sdkKey);
    }
};

module.exports = Stats;




