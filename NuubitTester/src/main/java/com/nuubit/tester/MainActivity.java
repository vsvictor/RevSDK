package com.nuubit.tester;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nuubit.sdk.views.ConfigFragment;
import com.nuubit.sdk.views.CountersFragment;
import com.nuubit.sdk.views.StatFragment;
import com.nuubit.tester.fragments.MainFragment;
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

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainListener {
    private Fragment main;

    private Fragment current;
    private Fragment old;

    private DrawerLayout drawer;

    private AppBarLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                getFragmentManager().beginTransaction().replace(R.id.llLeftDrawer, CountersFragment.newInstance()).commit();
            }

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        setTitle("");
        setSupportActionBar(toolbar);

        layout = (AppBarLayout) findViewById(R.id.app_bar);
        layout.setExpanded(true);

        main = MainFragment.newInstance();
        current = main;
        getFragmentManager().beginTransaction().replace(R.id.rlMainContainer, main).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main) {
            old = current;
            current = MainFragment.newInstance();
            getFragmentManager().beginTransaction().remove(old).replace(R.id.rlMainContainer, current).commit();
        } else if (id == R.id.config_view) {
            old = current;
            current = ConfigFragment.newInstance(1, NuubitApp.getInstance().getConfig());
            getFragmentManager().beginTransaction().remove(old).replace(R.id.rlMainContainer, current).commit();
        } else if (id == R.id.stat_view) {
            old = current;
            current = StatFragment.newInstance();
            getFragmentManager().beginTransaction().remove(old).replace(R.id.rlMainContainer, current).commit();
        } else if (id == R.id.log_view) {

        } else if (id == R.id.drawer) {
            drawer.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (current instanceof MainFragment) {
                super.onBackPressed();
            } else {
                current = MainFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.rlMainContainer, current).commit();
            }
        }
    }
    @Override
    public void onMain() {

    }
}
