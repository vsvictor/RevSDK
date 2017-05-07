package com.nuubit.racer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nuubit.racer.fragments.MainFragment;
import com.nuubit.racer.fragments.TaskFragment;
import com.nuubit.racer.fragments.WebTaskFragment;
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

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainListener,
        TaskFragment.OnTaskListener,
        WebTaskFragment.OnWebTaskListener {
    public static Fragment current;

    //private SharedPreferences settings;
    //private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        current = MainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, current)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (current instanceof TaskFragment) {
            current = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, current)
                    .commit();
        } else super.onBackPressed();
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
            //startActivity(new Intent(this, SettingActivity.class));
            NuubitApp.getInstance().email(NuubitApp.getInstance().getEMail(), this).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNativeMobile() {
        current = TaskFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, current)
                .commit();
    }

    @Override
    public void onWeb() {
        current = WebTaskFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, current)
                .commit();
    }

    @Override
    public void onStartTaskInSeries(int steps, long body, String url, String method, String type) {
        Intent intent = new Intent(this, ConsistentlyActivity.class);
        intent.putExtra(Const.STEPS, steps);
        intent.putExtra(Const.SIZE, body);
        intent.putExtra(Const.URL, url);
        intent.putExtra(Const.METHOD, method);
        intent.putExtra(Const.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onStartTaskParelelly(int steps, long body, String url, String method, String type) {
        Intent intent = new Intent(this, ParalellyActivity.class);
        intent.putExtra(Const.STEPS, steps);
        intent.putExtra(Const.SIZE, body);
        intent.putExtra(Const.URL, url);
        intent.putExtra(Const.METHOD, method);
        intent.putExtra(Const.TYPE, type);
        startActivity(intent);
    }
    @Override
    public void onStartUnlimTaskSeries(long body, String url, String method, String type) {
        Intent intent = new Intent(this, ConsistentlyActivity.class);
        intent.putExtra(Const.STEPS, -1);
        intent.putExtra(Const.SIZE, body);
        intent.putExtra(Const.URL, url);
        intent.putExtra(Const.METHOD, method);
        intent.putExtra(Const.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onWebStartTaskInSeries(int steps, long body, String url, String method, String type) {
        Intent intent = new Intent(this, ConsistentlyWebActivity.class);
        intent.putExtra(Const.STEPS, steps);
        //intent.putExtra(Const.STEPS, 1);
        intent.putExtra(Const.SIZE, body);
        intent.putExtra(Const.URL, url);
        intent.putExtra(Const.METHOD, method);
        intent.putExtra(Const.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onWebStartTaskParelelly(int steps, long body, String url, String method, String type) {
        Intent intent = new Intent(this, ParalellyWebActivity.class);
        intent.putExtra(Const.STEPS, steps);
        //intent.putExtra(Const.STEPS, 1);
        intent.putExtra(Const.SIZE, body);
        intent.putExtra(Const.URL, url);
        intent.putExtra(Const.METHOD, method);
        intent.putExtra(Const.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onWebStartUnlimTaskSeries(long body, String url, String method, String type) {

    }
}
