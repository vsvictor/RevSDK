

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
    },
    getOriginRequests: function (driver, position) {
        var request = undefined;
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Stats.list.config)
            .then(function (countersList) {
                request = countersList[position || 47].text();
                return request;
            });
    }
};

module.exports = Stats;




