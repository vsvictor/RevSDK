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


module.exports =  {
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
    }
};
