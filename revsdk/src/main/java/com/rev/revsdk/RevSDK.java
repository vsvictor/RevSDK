package com.rev.revsdk;

import android.util.Log;

import com.rev.revsdk.interseptor.RequestCreator;
import com.rev.revsdk.utils.Tag;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

public class RevSDK {
    private static final String TAG = RevSDK.class.getSimpleName();

    public static OkHttpClient OkHttpCreate(){
        return OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC);
    }

    public static OkHttpClient OkHttpCreate(int timeoutSec){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request result = null;
                Request original = chain.request();
                boolean systemRequest= isSystem(original);
                Log.i(TAG, "is System?"+String.valueOf(systemRequest)+" ,Intercepted: \n"+original.toString());
                if(!systemRequest)
                    result = processingRequest(original);
                else result = original;
                return chain.proceed(result);
            }
        }).connectTimeout(timeoutSec, TimeUnit.SECONDS);
        return httpClient.build();
    }

    private static boolean isSystem(Request req){
        boolean result = false;
        try{
            Tag tag = (Tag) req.tag();
            result = (boolean) tag.getValue();
        }catch (Exception ex){
            result = false;
        }
        return result;
    }

    private static Request processingRequest(Request original){
        Request result = null;
        RequestCreator creator = new RequestCreator(RevApplication.getInstance().getConfig());
        Log.i(TAG, "Intercepted: \n"+original.toString());
        return creator.create(original);
    }
}
