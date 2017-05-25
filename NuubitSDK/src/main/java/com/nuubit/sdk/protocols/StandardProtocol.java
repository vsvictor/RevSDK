package com.nuubit.sdk.protocols;

import android.content.Intent;
import android.util.Log;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.interseptor.ProgressResponseBody;
import com.nuubit.sdk.statistic.counters.ProtocolCounters;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;

import org.chromium.net.UrlRequest;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

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

    public StandardProtocol() {
        super();
        this.descroption = EnumProtocol.STANDARD;
        //standartCounter = NuubitApplication.getInstance().getProtocolCounters().get("standart");
        //originCounter = NuubitApplication.getInstance().getProtocolCounters().get("origin");
    }

    @Override
    public synchronized Response send(Interceptor.Chain chain) throws IOException {
        result = preHandler(chain);

        try {
            response = chain.proceed(result);
        } catch (Exception ex){
            beginTime = System.currentTimeMillis();
            response = chain.proceed(original);
            Log.i(TAG, "Edge fail. Redirect to origin");
            //ex.printStackTrace();
        }finally {
            Response r = postHandler();
            response = r;
        }
        return response;
    }

    @Override
    public TestOneProtocol test(String url) {
        TestOneProtocol res = new TestOneProtocol(EnumProtocol.STANDARD);
        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(new Tag(NuubitConstants.SYSTEM_REQUEST, true));
        builder.url(url);
        Call callback = NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false).newCall(builder.build());
        try {
            long begTime = System.currentTimeMillis();
            res.setStartTime(begTime);

            //Response response = callback.execute();
            synchronized (mutex) {
                callback.enqueue(new RealStandartCallback());
                mutex.wait();
            }
            long endTime = System.currentTimeMillis();
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() != HTTPCode.Type.SUCCESSFULL) {
                res.setTime(-1);
            } else {
                res.setTime(endTime - begTime);
            }
            res.setTimeEnded(endTime);
            res.setReason(code.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            res.setTime(-1);
            res.setTimeEnded(System.currentTimeMillis());
            res.setReason(NuubitConstants.UNDEFINED);
        }
        Log.i("TESTPROTOCOL",EnumProtocol.STANDARD.toString()+" "+res.toString());
        Log.i("TESTPROTOCOL",url);
        return res;
    }

    private class RealStandartCallback implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {
            synchronized (mutex){
                mutex.notifyAll();
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            synchronized (mutex){
                StandardProtocol.this.response = response;
                mutex.notifyAll();
            }
        }
    }
}
