package com.rev.weather.loader;

import android.content.Context;
import android.content.Loader;
import android.util.Log;

import com.rev.weather.IWeather;
import com.rev.weather.RevApp;
import com.rev.weather.model.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by victor on 29.03.17.
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
        api = RevApp.getAPI();
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
        super.onForceLoad();
        Call<Root> call = api.getWeatherByCoordinate(latitude, longitude, RevApp.getKey());
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    root = response.body();
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
    protected void onReset() {
        root = null;
    }
}
