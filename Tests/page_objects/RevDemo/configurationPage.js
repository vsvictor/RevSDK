module.exports = {

    lists: {
        config: {
            xpath: '//android.widget.TextView'
        }
    },

    getConfigList: function() {
        return elementByXPath(this.lists.config.xpath);
    }
};