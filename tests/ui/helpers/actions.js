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

var wd = require('wd');

// Function closeSettings swipes down to open Android settings
exports.openSettings = function () {
    var action = new wd.TouchAction();
    var opts = {startX:500, endX:500, startY:50, endY:1500, duration:600};
    action
        .press({x: opts.startX, y: opts.startY})
        .wait(opts.duration)
        .moveTo({x: opts.endX, y: opts.endY})
        .release();
    return this.performTouchAction(action);
};

// Function closeSettings swipes up to close Android settings
exports.closeSettings = function () {
    var action = new wd.TouchAction();
    var opts = {startX:500, endX:500, startY:1500, endY:50, duration:600};
    action
        .press({x: opts.startX, y: opts.startY})
        .wait(opts.duration)
        .moveTo({x: opts.endX, y: opts.endY})
        .release();
    return this.performTouchAction(action);
};

// Function scrollDown is scrolling down the list, in case some counter/value is not visible
exports.scrollDown = function () {
    var action = new wd.TouchAction();
    var opts = {startX:500, endX:500, startY:2300, endY:300, duration:500};
    action
        .press({x: opts.startX, y: opts.startY})
        .wait(opts.duration)
        .moveTo({x: opts.endX, y: opts.endY})
        .release();
    return this.performTouchAction(action);
};






