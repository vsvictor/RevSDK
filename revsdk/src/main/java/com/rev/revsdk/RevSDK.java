package com.rev.revsdk;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by victor on 12.02.17.
 */

public class RevSDK {
    private static final String TAG = RevSDK.class.getSimpleName();
    public static OkHttpClient OkHttpCreate(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Log.i(TAG, "Intercepted: \n"+original.toString());
                return chain.proceed(original);
            }
        });
        return httpClient.build();
    }
    public static OkHttpClient OkHttpCreate(int timeoutSec){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Log.i(TAG, "Intercepted: \n"+original.toString());
                return chain.proceed(original);
            }
        }).connectTimeout(timeoutSec, TimeUnit.SECONDS);
        return httpClient.build();
    }
}
