package com.rev.wather;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rev.wather.fragments.FiveDays;
import com.rev.wather.fragments.SixteenDays;
import com.rev.wather.fragments.TodayWather;

public class MainActivity extends AppCompatActivity implements
        TodayWather.OnTodayListener,
        FiveDays.OnFiveDaysListener,
        SixteenDays.OnSixteenDaysListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(0);
    }

    /*
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
    */
    @Override
    public void onToday(Uri uri) {

    }

    @Override
    public void onFiveDays(Uri uri) {

    }

    @Override
    public void onSixteetDays(Uri uri) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment result = null;
            switch (position) {
                case 0: {
                    result = TodayWather.newInstance();
                    break;
                }
                case 1: {
                    result = FiveDays.newInstance();
                    break;
                }
                case 2: {
                    result = SixteenDays.newInstance();
                    break;
                }
                default: {
                    result = TodayWather.newInstance();
                    break;
                }
            }
            return result;
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
