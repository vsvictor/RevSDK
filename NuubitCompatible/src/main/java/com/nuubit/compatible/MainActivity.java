package com.nuubit.compatible;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.nuubit.compatible.fragments.IUpdater;
import com.nuubit.compatible.fragments.PicassoFragment;
import com.nuubit.compatible.fragments.RetrofitFragment;
import com.nuubit.compatible.fragments.VolleyFragment;
import com.nuubit.compatible.loader.RootLoader;
import com.nuubit.compatible.model.Root;
import com.nuubit.compatible.permission.RequestUserPermission;

import java.util.ArrayList;
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

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LoaderManager.LoaderCallbacks<Root>,
        RetrofitFragment.OnRetrofitListener,
        PicassoFragment.OnPicassoListener,
        VolleyFragment.OnVolleyListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_STORAGE_INTERNET = 1;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 2;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private GoogleApiClient googleClient;
    private Location location;
    private IUpdater updater;

    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    private ArrayList<String> titles = new ArrayList<String>();

    private Root saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        list.add(RetrofitFragment.newInstance());
        list.add(PicassoFragment.newInstance());
        list.add(VolleyFragment.newInstance());
        updater = (IUpdater) list.get(0);
        titles.add(getResources().getString(R.string.today));
        titles.add(getResources().getString(R.string.day5));
        titles.add(getResources().getString(R.string.day16));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), list, titles);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updater.updateData(saved);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        if (googleClient == null) {
            googleClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        googleClient.connect();
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Gson gson = new Gson();
        String s = gson.toJson(saved, Root.class);
        savedInstanceState.putString("data", s);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Gson gson = new Gson();
        String s = savedInstanceState.getString("data");
        saved = gson.fromJson(s, Root.class);
    }
    @Override
    protected void onStop() {
        googleClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        run();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_COARSE_LOCATION) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        run();
                    } else {
                        RequestUserPermission.verifyPermissionsAccessCoarseLocation(this, REQUEST_ACCESS_COARSE_LOCATION);
                    }
                }
            }
        }
    }

    private void run() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(Const.RESULT_TAG, "Location: deny access");
            RequestUserPermission.verifyPermissionsAccessCoarseLocation(this, REQUEST_ACCESS_COARSE_LOCATION);
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleClient);
        if (location != null) {
            Log.i(Const.RESULT_TAG, "Location: latitude:" + location.getLatitude() + ", longitude: " + location.getLongitude());
            Bundle b = new Bundle();
            b.putDouble(Const.LATITUDE, location.getLatitude());
            b.putDouble(Const.LONGITUDE, location.getLongitude());
            getLoaderManager().restartLoader(R.id.today_loader, b, this);
            Log.i("Temp", "Restart");
        }
    }

    @Override
    public void onRetrofitLoad() {
        Log.i(TAG, "-----------------Retrofir LOADED!!!!!!!!!!!!!!!-----------------");
    }

    @Override
    public void onPicassoLoad(String url) {
        Log.i(TAG, "-----------------Picasso LOADED!!!!!!!!!!!!!!!-----------------\n" + url);
    }

    @Override
    public void onVolleyLoad() {
        Log.i(TAG, "-----------------Volley LOADED!!!!!!!!!!!!!!!-----------------");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        run();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public Loader<Root> onCreateLoader(int id, Bundle args) {
        double latitude = args.getDouble(Const.LATITUDE, 0);
        double longitude = args.getDouble(Const.LONGITUDE, 0);
        Loader<Root> loader = null;
        switch (id) {
            case R.id.today_loader: {
                Log.i(TAG, "Loader create");
                loader = new RootLoader(this, latitude, longitude);
                break;
            }
        }
        Log.i("Temp", "Loader " + loader == null ? "is null" : "not null");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Root> loader, Root root) {
        updater.updateData(root);
        saved = root;
        Log.i("Temp", "Loader finished");
    }

    @Override
    public void onLoaderReset(Loader<Root> loader) {
        loader.reset();
        Log.i("Temp", "Loader reset");
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> list;
        private ArrayList<String> titles;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
            super(fm);
            this.list = list;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                updater = (IUpdater) list.get(0);
                if (saved != null) updater.updateData(saved);
            }
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
