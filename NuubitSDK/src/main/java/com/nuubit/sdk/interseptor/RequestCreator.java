package com.nuubit.sdk.interseptor;

import android.util.Log;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.config.Config;
import com.nuubit.sdk.config.OperationMode;

import okhttp3.Headers;
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

public class RequestCreator {

    private static final String TAG = RequestCreator.class.getSimpleName();
    private final Config config;
    private DomainsChecker checker;
    public RequestCreator(Config config){
        this.config = config;
        checker = new DomainsChecker(this.config);
    }

    public Request create(Request original){
        Request result;

        if (config == null) {
            return original;
        }

        switch (config.getParam().get(0).getOperationMode()){
            case transfer_and_report:
            case transfer_only: {
                result = transfer(original);
                break;
            }
            case report_only:
            case off:
            default: {
                result = original;
                break;
            }
        }
        Log.i(TAG, config.getParam().get(0).getOperationMode().toString());
       return result;
    }

    private Request transfer(Request original) {

        if (checker.isInternalBlack(original) || checker.isBlack(original)) {
            return original;
        }

        if (checker.isWhite(original)) {
            Request.Builder builder = new Request.Builder();
            HttpUrl oldURL = original.url();
            String oldDomen = oldURL.host();

            HttpUrl newURL = HttpUrl.parse(oldURL.toString().replace(oldDomen,
                    NuubitApplication.getInstance().getSDKKey() + "." +
                            config.getParam().get(0).getEdgeSdkDomain()));
            if (!newURL.isHttps()) {
                newURL = HttpUrl.parse("https://" + newURL.toString().split("://")[1]);
            }
            Log.i(TAG, "-----------New URL: " + newURL + "---------------");
            builder.url(newURL).headers(addAllHeaders(original)).method(original.method(), original.body());
            return builder.build();
        }
        return original;
    }
    private Headers addAllHeaders(Request original){
        Headers.Builder builder = new Headers.Builder();
        StringBuilder valueBuilder = new StringBuilder();

        Headers oldHeaders = original.headers();

        for(String sName : oldHeaders.names()){
            String sValue = oldHeaders.get(sName);
            builder.add(sName, sValue);
        }

        if (NuubitApplication.getInstance().getConfig().getParam().get(0).getOperationMode() != OperationMode.off) {
            if (checker.isProvosion(original)) {
                if ("http".equalsIgnoreCase(original.url().scheme())) {
                    valueBuilder.append(NuubitApplication.getInstance().getSDKKey());
                    valueBuilder.append(".");
                    valueBuilder.append(NuubitApplication.getInstance().getConfig().getParam().get(0).getEdgeSdkDomain());
                    builder.add(NuubitConstants.HOST_HEADER_NAME, valueBuilder.toString());
                }
                builder.add(NuubitConstants.HOST_REV_HEADER_NAME, original.url().host());
                builder.add(NuubitConstants.PROTOCOL_REV_HEADER_NAME, original.url().scheme());
            } else if (checker.isWhite(original)) {
                valueBuilder.append(NuubitApplication.getInstance().getSDKKey());
                valueBuilder.append(".");
                valueBuilder.append(NuubitApplication.getInstance().getConfig().getParam().get(0).getEdgeSdkDomain());
                builder.add(NuubitConstants.HOST_HEADER_NAME, valueBuilder.toString());
                builder.add(NuubitConstants.HOST_REV_HEADER_NAME, original.url().host());
                builder.add(NuubitConstants.PROTOCOL_REV_HEADER_NAME, original.url().scheme());
            }
        }
        return builder.build();
    }
}
