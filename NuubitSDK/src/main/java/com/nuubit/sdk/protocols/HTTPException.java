package com.nuubit.sdk.protocols;

import android.util.Log;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.sections.RequestOne;

import okhttp3.Request;
import okhttp3.Response;

import static com.nuubit.sdk.NuubitSDK.isStatistic;
import static com.nuubit.sdk.NuubitSDK.isSystem;

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

public class HTTPException extends Exception {
    private Protocol protocol;

    public HTTPException(Request origin, Request transfered, Response response, Protocol protocol) {
        if (!isSystem(origin) && isStatistic()) {
            try {
                final RequestOne statRequest = RequestOne.toRequestOne(origin, transfered, response, NuubitApplication.getInstance().getBest().getDescription());
                NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
                Log.i("database", statRequest.toString());
            } catch (NullPointerException ex) {
                Log.i("database", "Database error!!!");
            }
        }
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }
}
