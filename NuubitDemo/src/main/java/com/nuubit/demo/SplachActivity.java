package com.nuubit.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.nuubit.sdk.NuubitActions;

import java.util.Timer;
import java.util.TimerTask;
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

public class SplachActivity extends AppCompatActivity {
    private static final String TAG = SplachActivity.class.getSimpleName();
    private RelativeLayout rlBridge;
    //private ImageView ivLogo;
    private Animation city;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        startTime = System.currentTimeMillis();
        rlBridge = (RelativeLayout) findViewById(R.id.rlBridge);
        //ivLogo = (ImageView) findViewById(R.id.ivLogo);
        city = AnimationUtils.loadAnimation(this, R.anim.city);

    }

    @Override
    protected void onResume() {
        super.onResume();
        rlBridge.startAnimation(city);
        //ivLogo.startAnimation(city);
        IntentFilter ifLoader = new IntentFilter();
        ifLoader.addAction(NuubitActions.CONFIG_LOADED);
        registerReceiver(loader, ifLoader);
        if (NuubitApp.getInstance().getConfig() != null)
            sendBroadcast(new Intent(NuubitActions.CONFIG_LOADED));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(loader);
    }

    private BroadcastReceiver loader = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long allTime = startTime - System.currentTimeMillis();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            }, 4000 - allTime);

        }

    };
}
