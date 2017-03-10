package com.rev.revsdk;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.rev.revsdk.config.Config;
import com.rev.revsdk.config.OperationMode;
import com.rev.revsdk.permission.RequestUserPermission;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.services.Configurator;
import com.rev.revsdk.services.Statist;
import com.rev.revsdk.services.Tester;
import com.rev.revsdk.statistic.RequestCounter;
import com.rev.revsdk.statistic.sections.Carrier;
import com.rev.revsdk.utils.DateTimeUtil;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

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

@ReportsCrashes(mailTo = "dvictor74@gmail.com",
        customReportContent = {
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
                ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST)
public class RevApplication extends Application {
    private static final String TAG = RevApplication.class.getSimpleName();
    private static RevApplication instance;
    private RequestUserPermission permission;
    private String sdkKey;
    private String version;
    private Config config;

    private SharedPreferences share;
    private Protocol best = Protocol.STANDART;

    private boolean configuratorRunning = false;
    private boolean isInternet = false;

    private BroadcastReceiver configReceiver;
    private BroadcastReceiver configStaleReceiver;
    private BroadcastReceiver testReceiver;
    private BroadcastReceiver statReceiver;

    private RequestCounter counter;

    private Timer configTimer = new Timer();
    private Timer staleTimer = new Timer();
    private Object monitor = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ACRA.init(this);
        share = getSharedPreferences("RevSDK", MODE_PRIVATE);
        counter = new RequestCounter();
        init();
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
        counter.load(share);
        config = Config.load(RevSDK.gsonCreate(), share);
        registration();
        configuratorRunner(true);
        testerRunner();
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
        }, 10 * 1000);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(configReceiver);
        unregisterReceiver(configStaleReceiver);
        unregisterReceiver(testReceiver);
        unregisterReceiver(statReceiver);
        counter.save(share);
    }

    public static RevApplication getInstance() {
        return instance;
    }

    public String getSDKKey() {
        return sdkKey;
    }

    public Protocol getBest() {
        return best;
    }

    public Config getConfig() {
        return config;
    }

    public String getVersion() {
        return version;
    }

    public String getPackage() {
        return getApplicationContext().getPackageName();
    }

    public RequestCounter getCounter() {
        return counter;
    }

    private void registration() {
        configReceiver = createConfigReceiver();
        configStaleReceiver = createStaleConfig();
        testReceiver = createTestReceiver();
        statReceiver = createStatReceiver();

        IntentFilter intentFilterConfig = new IntentFilter();
        intentFilterConfig.addAction(Actions.CONFIG_UPDATE_ACTION);
        registerReceiver(configReceiver, intentFilterConfig);

        IntentFilter intentFilterStaleConfig = new IntentFilter();
        intentFilterStaleConfig.addAction(Actions.CONFIG_LOADED);
        registerReceiver(configStaleReceiver, intentFilterStaleConfig);

        IntentFilter intentFilterTester = new IntentFilter();
        intentFilterTester.addAction(Actions.TEST_PROTOCOL_ACTION);
        registerReceiver(testReceiver, intentFilterTester);

        IntentFilter intentFilterStat = new IntentFilter();
        intentFilterStat.addAction(Actions.STAT_ACTION);
        registerReceiver(statReceiver, intentFilterStat);
    }

    private BroadcastReceiver createConfigReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                Bundle result = intent.getExtras();
                if (result != null) {
                    String newConfig = result.getString(Constants.CONFIG);
                    if (newConfig != null) {
                        Log.i(TAG + " configurator receiver", newConfig);
                        Gson gson = RevSDK.gsonCreate();
                        config = gson.fromJson(newConfig, Config.class);
                        config.save(gson, share);
                        sendBroadcast(new Intent(Actions.CONFIG_LOADED));
                        Log.i("System", "Config saved, mode: " + config.getParam().get(0).getOperationMode().toString());
                        if (RevSDK.isStatistic()) {
                            statRunner();
                        }
                        configuratorRunning = true;
                    }
                }
                configuratorRunner(false);
            }
        };
    }

    private BroadcastReceiver createStaleConfig() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                while (staleTimer != null) {
                    staleTimer.cancel();
                    staleTimer.purge();
                    staleTimer = null;
                }
                staleTimer = new Timer();
                long time = (config == null ?
                        Constants.DEFAULT_STALE_INTERVAL
                        : config.getParam().get(0).getConfigurationStaleTimeoutSec()) * 1000;

                staleTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        config.getParam().get(0).setOperationMode(OperationMode.off);
                        Log.i(TAG, "Off SDK");
                    }
                }, time);
                Log.i(TAG, "Stale timeout: " + time);
                Log.i(TAG, "Reset stale timer: " + DateTimeUtil.getDateCurrentTimeZone(System.currentTimeMillis()));
            }
        };
    }

    private BroadcastReceiver createTestReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String sProtocol = bundle.getString(Constants.TEST_PROTOCOL, getBest().toString());
                        best = Protocol.fromString(sProtocol);
                        Log.i(TAG + " test receiver", "Best protocol: " + best.toString());
                    }
                }
            }
        };
    }

    private BroadcastReceiver createStatReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    String sResponce = bundle.getString(Constants.STATISTIC);
                    Log.i(TAG + " statistic receiver", "Response: " + sResponce);
                }
                if (RevSDK.isStatistic()) {
                    Timer statTimer = new Timer();
                    statTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            statRunner();
                        }
                    }, config.getParam().get(0).getStatsReportingIntervalSec() * 1000);
                }
            }
        };
    }

    private void configuratorRunner(boolean now) {
        final Intent updateIntent = new Intent(RevApplication.this, Configurator.class);
        updateIntent.putExtra(Constants.CONFIG, config == null ?
                Constants.MAIN_CONFIG_URL : config.getParam().get(0).getConfigurationApiUrl());
        updateIntent.putExtra(Constants.TIMEOUT, config == null ?
                Constants.DEFAULT_TIMEOUT_SEC : config.getParam().get(0).getConfigurationRequestTimeoutSec());
        if (now) {
            startService(updateIntent);
        } else {
            //Timer configTimer = new Timer();
            while (configTimer != null) {
                configTimer.cancel();
                configTimer.purge();
                configTimer = null;
            }
            configTimer = new Timer();
            if (configTimer == null) configTimer = new Timer();
            configTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startService(updateIntent);

                }
            }, config == null ?
                    Constants.DEFAULT_CONFIG_INTERVAL
                    : config.getParam().get(0).getConfigurationRefreshIntervalSec() * 1000);
        }
    }

    private void statRunner() {
        String isStatistic = "Statistic is off";
        Log.i(TAG, String.valueOf(RevSDK.isStatistic()));
        if (config != null && RevSDK.isStatistic()) {
            Intent statIntent = new Intent(RevApplication.this, Statist.class);
            statIntent.putExtra(Constants.TIMEOUT,
                    config.getParam().get(0).getConfigurationStaleTimeoutSec());
            statIntent.putExtra(Constants.STATISTIC, config.getParam().get(0).getStatsReportingUrl());
            startService(statIntent);
            isStatistic = "Run statistic";
        }
        Log.i(TAG, isStatistic);
    }

    private void testerRunner() {
        Intent statIntent = new Intent(RevApplication.this, Tester.class);
        startService(statIntent);
    }
}
