

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
    getOriginRequests: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Stats.list.config)
            .then(function (countersList) {
                return countersList[47].text().then(function (value) {
                    return value === 'originRequests' ? countersList[48].text() : countersList[47].text();
                });
            });
    },
    getRevRequests: function (driver) {
        return driver
            .waitForResponse(driver)
            .elementsByXPath(Stats.list.config)
            .then(function (countersList) {
                return countersList[45].text().then(function (value) {
                    return value === 'revRequests' ? countersList[46].text() : countersList[45].text();
                });
            });
    }
};

module.exports = Stats;




