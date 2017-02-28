package com.rev.revsdk;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rev.revsdk.config.Config;
import com.rev.revsdk.database.DBHelper;
import com.rev.revsdk.permission.PostPermissionGranted;
import com.rev.revsdk.permission.RequestUserPermission;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.services.Configurator;
import com.rev.revsdk.services.Statist;
import com.rev.revsdk.services.Tester;
import com.rev.revsdk.statistic.sections.Carrier;
//import com.rev.revsdk.statistic.sections.helper.RSSIHelper;

import java.sql.Time;
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

public class RevApplication extends Application{
    private static final String TAG = RevApplication.class.getSimpleName();
    private static RevApplication instance;
    private RequestUserPermission permission;
    private String sdkKey;
    private String version;
    private Config config;
    private boolean firstActivity;

    private SharedPreferences share;
    private Protocol best = Protocol.STANDART;

    private boolean isInternet = false;

    private BroadcastReceiver configReceiver;
    private BroadcastReceiver testReceiver;
    private BroadcastReceiver statReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        firstActivity = true;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                share = getSharedPreferences("RevSDK", MODE_PRIVATE);
                if (firstActivity) {
                    permission = new RequestUserPermission(activity);
                    permission.verifyPermissionsInternet(new PostPermissionGranted() {
                        @Override
                        public void onPermissionGranted() {
                            init();
                            isInternet = true;
                            firstActivity = false;
                        }
                        @Override
                        public void onPermissionDenied() {
                            isInternet = false;
                            firstActivity = true;
                            Toast.makeText(RevApplication.this, RevApplication.this.getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                        }
                    });
                    permission.verifyPermissionsReadPhoneState();
                    permission.verifyPermissionsAccessNetworkState();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(final Activity activity) {
                if(isInternet) {
                    registration();
                    configuratorRunner();
                    testerRunner();
                }
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Carrier.runRSSIListener();
                            }
                        }).start();

                    }
                }, 10*1000);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if(isInternet) {
                    unregisterReceiver(configReceiver);
                    unregisterReceiver(testReceiver);
                    unregisterReceiver(statReceiver);
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private String getKeyFromManifest() {
        String result = "key";
        try {
            ApplicationInfo app = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            for (String key : bundle.keySet()) {
                if (key.equals("com.revsdk.key")) {
                    result = bundle.getString(Constants.KEY_TAG);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result.toLowerCase();
    }

    private void init() {
        firstActivity = true;
        try {
            version = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext()
                    .getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            version = "1.0";
        }
        sdkKey = getKeyFromManifest();
        config = Config.load(RevSDK.gsonCreate(), share);
        if(config != null){
            statRunner();
        }
    }
    @Override
    public void onTerminate(){
        super.onTerminate();
    }

    public static RevApplication getInstance(){
        return instance;
    }
    public String getSDKKey(){
        return sdkKey;
    }
    public Protocol getBest(){
        return best;
    }
    public Config getConfig(){return config;}
    public String getVersion() {return version;}
    public String getPackage(){return getApplicationContext().getPackageName();}
    public RequestUserPermission getPermission(){return permission;}

    private void registration(){
        configReceiver = createConfigReceiver();
        testReceiver = createTestReceiver();
        statReceiver = createStatReceiver();

        IntentFilter intentFilterConfig = new IntentFilter();
        intentFilterConfig.addAction(Actions.CONFIG_UPDATE_ACTION);
        registerReceiver(configReceiver, intentFilterConfig);

        IntentFilter intentFilterTester = new IntentFilter();
        intentFilterTester.addAction(Actions.TEST_PROTOCOL_ACTION);
        registerReceiver(testReceiver, intentFilterTester);

        IntentFilter intentFilterStat = new IntentFilter();
        intentFilterStat.addAction(Actions.STAT_ACTION);
        registerReceiver(statReceiver, intentFilterStat);
    }
    private BroadcastReceiver createConfigReceiver(){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                Bundle result = intent.getExtras();
                if(result != null){
                    String newConfig = result.getString(Constants.CONFIG);
                    if(newConfig != null){
                        Log.i(TAG+" receiver", newConfig);
                        Gson gson = RevSDK.gsonCreate();
                        config = gson.fromJson(newConfig, Config.class);
                        config.save(gson,share);
                        Log.i(TAG, "Config saved");
                        statRunner();
                    }
                    configuratorRunner();
                }
            }
        };
    }
    private BroadcastReceiver createTestReceiver(){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null){
                  Bundle bundle = intent.getExtras();
                  if(bundle != null){
                      String sProtocol = bundle.getString(Constants.TEST_PROTOCOL, getBest().toString());
                      best = Protocol.fromString(sProtocol);
                      Log.i(TAG+" test receiver", "Best protocol: "+best.toString());
                  }
              }
            }
        };
    }
    private BroadcastReceiver createStatReceiver(){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null){
                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                        String sResponce = bundle.getString(Constants.STATISTIC);
                        Log.i(TAG+" statistic receiver", "Response: "+sResponce);
                    }
                }
            }
        };
    }
    private void configuratorRunner(){
        Timer configTimer = new Timer();
        configTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent updateIntent = new Intent(RevApplication.this, Configurator.class);
                updateIntent.putExtra(Constants.CONFIG,config==null?
                        Constants.MAIN_CONFIG_URL:config.getParam().get(0).getConfigurationApiUrl());
                updateIntent.putExtra(Constants.TIMEOUT, config == null?
                        Constants.DEFAULT_TIMEOUT_SEC:config.getParam().get(0).getConfigurationRequestTimeoutSec());
                startService(updateIntent);

            }
        }, config == null?
                0:config.getParam().get(0).getConfigurationRefreshIntervalSec()*1000);
    }
    private void statRunner(){
        Timer statTimer = new Timer();
        statTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent statIntent = new Intent(RevApplication.this, Statist.class);
                statIntent.putExtra(Constants.TIMEOUT,
                        config.getParam().get(0).getConfigurationStaleTimeoutSec());
                statIntent.putExtra(Constants.STATISTIC, config.getParam().get(0).getStatsReportingUrl());
                startService(statIntent);
            }
        },0, config.getParam().get(0).getStatsReportingIntervalSec()*1000);
    }
    private void testerRunner(){
                Intent statIntent = new Intent(RevApplication.this, Tester.class);
                startService(statIntent);
    }
}
