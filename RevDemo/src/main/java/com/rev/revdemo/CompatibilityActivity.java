package com.rev.revdemo;

import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.rev.revdemo.fragments.weather.FiveDaysFragment;
import com.rev.revdemo.fragments.weather.SixteenDaysFragment;
import com.rev.revdemo.fragments.weather.TodayFragment;
import com.rev.revdemo.fragments.weather.model.WeatherMain;

public class CompatibilityActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LoaderManager.LoaderCallbacks<WeatherMain>,
        TodayFragment.OnTodayListener,
        FiveDaysFragment.OnFiveDaysListener,
        SixteenDaysFragment.OnSixteenDaysListener {
    private static final String TAG = CompatibilityActivity.class.getSimpleName();
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private GoogleApiClient googleClient;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibility);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.weather);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
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
    protected void onStop() {
        googleClient.disconnect();
        super.onStop();
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_compatibility, menu);
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
    */
    @Override
    public void onTodayWeather(Uri uri) {
    }

    @Override
    public void onFiveDaysWeather(Uri uri) {
    }

    @Override
    public void onSixteenDaysWeather(Uri uri) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i(TAG, "Location: deny");
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleClient);
        if (location != null) {
            Log.i(TAG, "Location: latitude:" + location.getLatitude() + ", longitude: " + location.getLongitude());
            //getLoaderManager().initLoader(R.id.today_loader, Bundle.EMPTY, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Loader<WeatherMain> loader = null;
        switch (id) {
            case R.id.today_loader: {
                break;
            }
            default: {
                loader = null;
                break;
            }
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<WeatherMain> loader, WeatherMain data) {

    }

    @Override
    public void onLoaderReset(Loader<WeatherMain> loader) {
        loader.reset();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TodayFragment.newInstance();
                case 1:
                    return FiveDaysFragment.newInstance();
                case 2:
                    return SixteenDaysFragment.newInstance();
                default:
            }
            return TodayFragment.newInstance();
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.today);
                case 1:
                    return getResources().getString(R.string.day5);
                case 2:
                    return getResources().getString(R.string.day16);
            }
            return null;
        }
    }
}
