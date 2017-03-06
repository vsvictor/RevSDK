package com.rev.revdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.rev.revsdk.Actions;

import java.util.Timer;
import java.util.TimerTask;

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
        ifLoader.addAction(Actions.CONFIG_LOADED);
        registerReceiver(loader, ifLoader);
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
