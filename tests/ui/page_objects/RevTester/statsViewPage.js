

"use strict";

var Stats = {
    list: {
        config: '//android.widget.TextView'
    },

    getStatsList: function (driver) {
        return driver
            .elementsByXPath(Stats.list.config);
    },

    getSdkKey: function (driver) {
        return driver
            .sleep(2000)
            .elementsByXPath(Stats.list.config)
            .at(7);
    }
};

module.exports = Stats;




