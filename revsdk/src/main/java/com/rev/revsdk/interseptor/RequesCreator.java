package com.rev.revsdk.interseptor;

import android.preference.PreferenceActivity;

import com.rev.revsdk.Constants;
import com.rev.revsdk.RevApplication;
import com.rev.revsdk.config.Config;
import com.rev.revsdk.config.ListString;
import com.rev.revsdk.protocols.Protocol;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.internal.framed.Header;

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

public class RequesCreator {

    private final Config config;

    public RequesCreator(Config config){
        this.config = config;
    }

    public Request create(Request original){
        Request result = null;
        Request.Builder builder = new Request.Builder();
        HttpUrl oldURL = original.url();
        String oldDomen = oldURL.host();

        switch (config.getParam().get(0).getOperationMode()){
            case transfer_and_report:{
                String newURL = oldURL.toString().replace(oldDomen, config.getParam().get(0).getEdgeSdkDomain());
                builder.url(newURL).headers(addHeaders(original)).method(original.method(), original.body());
                result = builder.build();
                int i = 0;
                break;
            }
            case transfer_only:{break;}
            case report_only:{break;}
            case off:{
                result = original;
                break;
            }
        }
       return result;
    }
    private Headers addHeaders(Request original){
        Headers.Builder builder = new Headers.Builder();
        StringBuilder valueBuilder = new StringBuilder();

        Headers oldHeaders = original.headers();

        for(String sName : oldHeaders.names()){
            String sValue = oldHeaders.get(sName);
            builder.add(sName, sValue);
        }

        ListString provision = config.getParam().get(0).getDomainsProvisionedList();
        ListString white = config.getParam().get(0).getDomainsWhiteList();

        if(provision.contains(original.url().host())){
            if(original.url().scheme().toLowerCase().equals("http")){
                valueBuilder.append(RevApplication.getInstance().getSDKKey());
                valueBuilder.append(".");
                valueBuilder.append(RevApplication.getInstance().getConfig().getParam().get(0).getEdgeHost());
                builder.add(Constants.HOST_HEADER_NAME,valueBuilder.toString());
            }
            builder.add(Constants.HOST_REV_HEADER_NAME,original.url().host());
            builder.add(Constants.PROTOCOL_REV_HEADER_NAME,original.url().scheme());
        }
        else if(white.contains(original.url().host()) || white.isEmpty()){
            valueBuilder.append(RevApplication.getInstance().getSDKKey());
            valueBuilder.append(".");
            valueBuilder.append(RevApplication.getInstance().getConfig().getParam().get(0).getEdgeHost());
            builder.add(Constants.HOST_HEADER_NAME,valueBuilder.toString());
            builder.add(Constants.HOST_REV_HEADER_NAME,original.url().host());
            builder.add(Constants.PROTOCOL_REV_HEADER_NAME,original.url().scheme());
        }
        return builder.build();
    }
}
