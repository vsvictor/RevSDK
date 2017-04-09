package com.rev.weather.loader;

import android.content.Context;
import android.content.Loader;
import android.util.Log;

import com.rev.weather.IWeather;
import com.rev.weather.NuubitApp;
import com.rev.weather.model.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

public class RootLoader extends Loader<Root> {
    private static final String TAG = RootLoader.class.getSimpleName();
    private Root root;
    private double latitude;
    private double longitude;
    private IWeather api;

    public RootLoader(Context context, double latitude, double longitude) {
        super(context);
        this.latitude = latitude;
        this.longitude = longitude;
        api = NuubitApp.getAPI();
        root = null;
    }

    @Override
    protected void onStartLoading() {
        if (root != null) {
            deliverResult(root);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        Call<Root> call = api.getWeatherByCoordinate(latitude, longitude, NuubitApp.getKey());
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    root = response.body();
                    Log.i(TAG, root.toString());
                    deliverResult(root);
                } else {
                    deliverResult(null);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                deliverResult(null);
            }
        });
    }

    @Override
    public void deliverResult(Root r) {
        if (isReset()) {
            r = null;
            return;
        }
        if (isStarted()) {
            super.deliverResult(root);
        }
        root = r;
    }

    @Override
    protected void onStopLoading() {
        root = null;
    }
    @Override
    protected void onReset() {
        root = null;
    }
}
