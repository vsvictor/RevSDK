package com.rev.revdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rev.revdemo.fragments.FiveDaysFragment;
import com.rev.revdemo.fragments.SixteenDaysFragment;
import com.rev.revdemo.fragments.TodayFragment;

public class CompatibilityActivity extends AppCompatActivity implements
        TodayFragment.OnTodayListener,
        FiveDaysFragment.OnFiveDaysListener,
        SixteenDaysFragment.OnSixteenDaysListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

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
                    return TodayFragment.newInstance();
            }
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
