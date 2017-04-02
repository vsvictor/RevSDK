package com.rev.sdk.interseptor;

import android.util.Log;

import com.rev.sdk.config.Config;
import com.rev.sdk.config.ConfigParamenetrs;
import com.rev.sdk.config.ListString;

import okhttp3.HttpUrl;
import okhttp3.Request;

/*
 * ************************************************************************
 *
 *
 * NUU:BIT CONFIDENTIAL
 * [2013] - [2017] NUU:BIT, INC.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of NUU:BIT, INC. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to NUU:BIT, INC.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from NUU:BIT, INC.
 *
 * Victor D. Djurlyak, 2017
 *
 * /
 */

public class DomainsChecker {

    private static final String TAG = DomainsChecker.class.getSimpleName();
    private ListString internBlack;
    private ListString black;
    private ListString white;
    private ListString provision;

    public DomainsChecker(Config config) {
        try {
            ConfigParamenetrs params = config.getParam().get(0);
            this.internBlack = params.getInternalDomainsBlackList();
            this.black = params.getDomainsBlackList();
            this.white = params.getDomainsWhiteList();
            this.provision = params.getDomainsProvisionedList();
        } catch (NullPointerException ex) {
            Log.i(TAG, "ConfigParameters is null!!!");
        }
    }

    public boolean isInternalBlack(Request req) {
        return internBlack.contains(getDomain(req));
    }

    public boolean isBlack(Request req) {
        return black.contains(getDomain(req));
    }

    public boolean isProvosion(Request req) {
        return provision.contains(getDomain(req));
    }

    public boolean isWhite(Request req) {
        if (white.isEmpty()) return true;
        else return white.contains(getDomain(req));
    }

    private String getDomain(Request req) {
        HttpUrl url = req.url();
        return url.host();
    }

}
