package com.nuubit.sdk.protocols;

import android.content.Intent;
import android.util.Log;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.nuubit.sdk.NuubitSDK.isFree;
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

public class StandardProtocol extends Protocol {
    private static final String TAG = StandardProtocol.class.getSimpleName();
    private HTTPException prevException;
    public StandardProtocol() {
        this.descroption = EnumProtocol.STANDART;
    }

    @Override
    public synchronized Response send(Interceptor.Chain chain) throws IOException {
        Request result = null;
        Request original = chain.request();
        boolean systemRequest = isSystem(original);
        boolean freeRequest = isFree(original);
        if (!systemRequest && !freeRequest) {
            result = NuubitSDK.processingRequest(original, true);
        } else {
            Log.i("System", original.toString());
            result = original;
        }
        Response response = null;
        try {
            response = chain.proceed(result);
            if (response == null) {
                throw new HTTPException(original, result, response, this);
            }
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() == HTTPCode.Type.SERVER_ERROR) {
                throw new HTTPException(original, result, response, this);
            }

            if (!isSystem(original) && isStatistic()) {
                try {
                    final RequestOne statRequest = RequestOne.toRequestOne(original, result, response, NuubitApplication.getInstance().getBest().getDescription());
                    NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
                    Log.i("database", statRequest.toString());
                } catch (NullPointerException ex) {
                    Log.i("database", "Database error!!!");
                }

            }
            if (!isSystem(original)) {
                this.zeroing();
            }
        } catch (HTTPException ex) {
            response = chain.proceed(original);
            this.errorIncrement();
            if (this.isOverflow()) {
                NuubitApplication.getInstance().removeProtocol(EnumProtocol.createInstance(this.getDescription()));
                NuubitApplication.getInstance().sendBroadcast(new Intent(NuubitActions.RETEST));
                this.zeroing();
            }
            ex.printStackTrace();
        }
        Log.i(TAG, "Response:" + response.toString());
        NuubitApplication.getInstance().getRequestCounter().addRequest(response.request(), EnumProtocol.STANDART);
        return response;
    }

    @Override
    public TestOneProtocol test(String url) {
        TestOneProtocol res = new TestOneProtocol(EnumProtocol.STANDART);
        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true));
        Call callback = NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false).newCall(builder.build());
        try {
            Response response = callback.execute();
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() != HTTPCode.Type.SUCCESSFULL) {
                res.setTime(-1);
            } else {
                res.setTime(response.receivedResponseAtMillis() - response.sentRequestAtMillis());
            }
            res.setTimeEnded(System.currentTimeMillis());
            res.setReason(code.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            res.setTime(-1);
            res.setTimeEnded(System.currentTimeMillis());
            res.setReason(NuubitConstants.UNDEFINED);
        }
        return res;
    }
}
