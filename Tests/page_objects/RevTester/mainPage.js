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
        methods: 'com.rev.revtester:id/spMethod',
        operationModes: 'com.rev.revtester:id/spMode'
    },
    list: {
        methods: 'android.widget.TextView',
        operationModes: 'android.widget.TextView'
    },
    input: {
        url: 'com.rev.revtester:id/tlQuery'
    },
    button: {
        send: 'com.rev.revtester:id/rlRun'
    },
    output: {
        response: 'com.rev.revtester:id/tvMain'
    }
};
