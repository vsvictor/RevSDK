module.exports =  {
    menuBtn: {
        button: {
            className: 'android.widget.ImageButton'
        }
    },
    menuOptions:{
        configurationView: {
            xpath: '//android.widget.CheckedTextView[@text=\'Configuration view\']'
        },
    lists:{
        config: {
            xpath: '//android.widget.TextView'
         }
    }
    },

    getMenuBtn: function() {
        return elementClassName(this.menuBtn.button.className);
    },

    clickMenuBtn: function() {
        return this getMenuBtn().click();
    },

    getMainBtn: function() {
        return elementByXPath(this.menuOptions.main.xpath);
    },

    clickMainBtn: function() {
        return this getMainBtn().click();
    },

    getConfigViewBtn: function() {
        return elementByXPath(this.menuOptions.configurationView.xpath);
    },

    clickConfigViewBtn: function() {
        return this getConfigViewBtn().click();
    },

    getConfigList: function() {
        return elementByXPath(this.lists.config.xpath);
    }

};
