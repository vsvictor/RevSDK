package com.rev.revsdk.interseptor;

import com.rev.revsdk.config.Config;
import com.rev.revsdk.config.ConfigParamenetrs;
import com.rev.revsdk.config.ListString;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by victor on 04.03.17.
 */

public class DomainsChecker {

    private ListString internBlack;
    private ListString black;
    private ListString white;
    private ListString provision;

    public DomainsChecker(Config config) {
        ConfigParamenetrs params = config.getParam().get(0);
        this.internBlack = params.getInternalDomainsBlackList();
        this.black = params.getDomainsBlackList();
        this.white = params.getDomainsWhiteList();
        this.provision = params.getDomainsProvisionedList();
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
