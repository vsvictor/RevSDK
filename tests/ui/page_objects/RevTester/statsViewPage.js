

"use strict";

var Stats = {
    list: {
        config: '//android.widget.TextView'
    },

    getStatsList: function (driver) {
        return driver
            .elementsByXPath(Config.list.config);
    },

    getSdkKey: function (driver) {
        return driver
            .sleep(2000)
            .elementsByXPath(Stats.list.config)
            .at(7);
    },
    getOriginRequests: function (driver) {
        return driver
            .sleep(3000)
            .elementsByXPath(Stats.list.config).at(152);
    }
};

module.exports = Stats;




