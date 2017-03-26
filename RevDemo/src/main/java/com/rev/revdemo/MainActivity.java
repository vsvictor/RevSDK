package com.rev.revdemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.rev.revdemo.fragments.ConfigFragment;
import com.rev.revdemo.fragments.MainFragment;
import com.rev.revdemo.fragments.StatFragment;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnMainListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment current;
    private Fragment old;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.rev_app_demo);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        current = MainFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.rlMainContainer, current).commit();
        startActivity(new Intent(this, SplachActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (current instanceof MainFragment) {
                WebView browser = ((MainFragment) current).getBrowser();
                if(browser != null){
                    if(browser.canGoBack()) browser.goBack();
                    else super.onBackPressed();
                }
                //super.onBackPressed();
            }
            else {
                current = MainFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.rlMainContainer, current).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main) {
            old = current;
            current = MainFragment.newInstance();
            getFragmentManager().beginTransaction().remove(old).replace(R.id.rlMainContainer, current).commit();
        } else if (id == R.id.config_view) {
            old = current;
            current = ConfigFragment.newInstance(1, RevApp.getInstance().getConfig());
            getFragmentManager().beginTransaction().remove(old).replace(R.id.rlMainContainer, current).commit();
        } else if (id == R.id.stat_view) {
            old = current;
            current = StatFragment.newInstance();
            getFragmentManager().beginTransaction().remove(old).replace(R.id.rlMainContainer, current).commit();
        } else if (id == R.id.log_view) {

        } else if (id == R.id.compatibility) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.rev.weather");
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
        } else if (id == R.id.tester) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.rev.revtester");
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMain() {
    }
}















