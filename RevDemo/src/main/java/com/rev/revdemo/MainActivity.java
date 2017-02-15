package com.rev.revdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rev.revsdk.statistic.Phone;
import com.rev.revsdk.statistic.Statistic;
import com.rev.revsdk.statistic.serialize.PhoneSerialize;
import com.rev.revsdk.statistic.serialize.StatisticSerializer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
}
