package com.nuubit.sdk;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.nuubit.sdk.config.Config;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.database.DBHelper;
import com.nuubit.sdk.permission.RequestUserPermission;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.protocols.Protocol;
import com.nuubit.sdk.protocols.StandardProtocol;
import com.nuubit.sdk.services.Configurator;
import com.nuubit.sdk.services.Statist;
import com.nuubit.sdk.services.Tester;
import com.nuubit.sdk.statistic.RequestCounter;
import com.nuubit.sdk.statistic.sections.Carrier;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

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

public class NuubitApplication extends Application implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = NuubitApplication.class.getSimpleName();
    private static NuubitApplication instance;
    private GoogleApiClient googleClient;
    private String sdkKey;
    private String version;
    private Config config;

    private SharedPreferences share;
    private Protocol best = new StandardProtocol();

    //private boolean configuratorRunning = false;
    private boolean isInternet = false;

    private BroadcastReceiver configReceiver;
    private BroadcastReceiver configStaleReceiver;
    private BroadcastReceiver testReceiver;
    private BroadcastReceiver statReceiver;

    private RequestCounter counter;

    private Timer configTimer = new Timer();
    private Timer staleTimer = new Timer();
    private Timer statTimer = new Timer();
    private Timer retestTimer = new Timer();

    protected String MAIN_PACKAGE;

    private DBHelper dbHelper;

    //private ArrayList<Protocol> allowed_protocols;
    private ArrayBlockingQueue<Protocol> allowed_protocols;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //allowed_protocols = new ArrayList<Protocol>();
        MAIN_PACKAGE = instance.getPackage();
        share = getSharedPreferences("NuubitSDK", MODE_PRIVATE);
        dbHelper = new DBHelper(this);
        counter = new RequestCounter();
        if (googleClient == null) {
            googleClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        init();
    }

    public static String getMainPackage() {
        return instance.getPackage();
    }

    private String getKeyFromManifest() {
        String result = "key";
        try {
            ApplicationInfo app = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            for (String key : bundle.keySet()) {
                if (key.equals("com.nuubit.key")) {
                    result = bundle.getString(NuubitConstants.KEY_TAG);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result.toLowerCase();
    }


    private String getNameFromManifest() {
        String result = "name";
        try {
            ApplicationInfo app = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            for (String key : bundle.keySet()) {
                if (key.equals("com.revsdk.name")) {
                    result = bundle.getString(NuubitConstants.KEY_TAG);
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
        config = Config.load(NuubitSDK.gsonCreate(), share);
        if (config == null) config = new Config();
        String transport = config.getParam().get(0).getInitialTransportProtocol();
        best = EnumProtocol.createInstance(EnumProtocol.fromString(transport));
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                RequestUserPermission.verifyPermissionsInternet(activity);
                RequestUserPermission.verifyPermissionsReadPhoneState(activity);
                RequestUserPermission.verifyPermissionsAccessNetworkState(activity);
                RequestUserPermission.verifyPermissionsAccessCoarseLocation(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                googleClient.connect();
            }

            @Override
            public void onActivityResumed(Activity activity) {
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
                testerRunner();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                shutdown();
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

    @Override
    public void onTerminate() {
        super.onTerminate();
        shutdown();
    }

    public static NuubitApplication getInstance() {
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

    public DBHelper getDatabase() {
        return dbHelper;
    }

    public synchronized void removeProtocol(Protocol protocol) {

        for (Protocol p : allowed_protocols) {
            if (p.getDescription() == protocol.getDescription()) {
                allowed_protocols.remove(p);
            }
        }

        while (retestTimer != null) {
            retestTimer.cancel();
            retestTimer.purge();
            retestTimer = null;
        }

        retestTimer = new Timer();
        retestTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                allowed_protocols.clear();
                for (EnumProtocol proto : getConfig().getParam().get(0).getAllowedTransportProtocols()) {
                    allowed_protocols.add(EnumProtocol.createInstance(proto));
                }
                sendBroadcast(new Intent(NuubitActions.RETEST));
            }
        }, NuubitConstants.PENALTY_TIME_SEC * 1000);
    }

    public Location getLocation() {
        Location location;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleClient);
        if (location != null) {
            Log.i(TAG, "Location: latitude:" + location.getLatitude() + ", longitude: " + location.getLongitude());
        }
        return location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void registration() {
        configReceiver = createConfigReceiver();
        configStaleReceiver = createStaleConfig();
        testReceiver = createTestReceiver();
        statReceiver = createStatReceiver();

        IntentFilter intentFilterConfig = new IntentFilter();
        intentFilterConfig.addAction(NuubitActions.CONFIG_UPDATE_ACTION);
        registerReceiver(configReceiver, intentFilterConfig);

        IntentFilter intentFilterNoConfig = new IntentFilter();
        intentFilterNoConfig.addAction(NuubitActions.CONFIG_NO_UPDATE_ACTION);
        registerReceiver(noUpdate, intentFilterNoConfig);

        IntentFilter intentFilterStaleConfig = new IntentFilter();
        intentFilterStaleConfig.addAction(NuubitActions.CONFIG_LOADED);
        registerReceiver(configStaleReceiver, intentFilterStaleConfig);

        IntentFilter intentFilterTester = new IntentFilter();
        intentFilterTester.addAction(NuubitActions.TEST_PROTOCOL_ACTION);
        registerReceiver(testReceiver, intentFilterTester);

        IntentFilter intentFilterStat = new IntentFilter();
        intentFilterStat.addAction(NuubitActions.STAT_ACTION);
        registerReceiver(statReceiver, intentFilterStat);
        //Log.i("PROMO", config.getAppName() + ": Start all receivers");

        IntentFilter ifn = new IntentFilter();
        ifn.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ifn.addAction(NuubitActions.RETEST);
        registerReceiver(netListener, ifn);
    }

    public void shutdown() {
        while (configTimer != null) {
            configTimer.cancel();
            configTimer.purge();
            configTimer = null;
        }
        while (staleTimer != null) {
            staleTimer.cancel();
            staleTimer.purge();
            staleTimer = null;
        }
        while (statTimer != null) {
            statTimer.cancel();
            statTimer.purge();
            statTimer = null;
        }
        unregisterReceiver(configReceiver);
        unregisterReceiver(noUpdate);
        unregisterReceiver(configStaleReceiver);
        unregisterReceiver(testReceiver);
        unregisterReceiver(statReceiver);
        unregisterReceiver(netListener);
        counter.save(share);
        //Log.i("PROMO", config.getAppName() + ": Shutdown all receivers");
    }

    private BroadcastReceiver createConfigReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                Bundle result = intent.getExtras();
                if (result != null) {
                    HTTPCode httpCode = HTTPCode.create(result.getInt(NuubitConstants.HTTP_RESULT, HTTPCode.UNDEFINED.getCode()));
                    if (httpCode.getType() == HTTPCode.Type.SUCCESSFULL) {
                        Log.i(TAG, "HTTP config success");
                        String newConfig = result.getString(NuubitConstants.CONFIG);
                        Log.i(TAG, "New Config: " + newConfig);
                        if (newConfig != null) {
                            Log.i(TAG + " configurator receiver", newConfig);
                            Gson gson = NuubitSDK.gsonCreate();
                            Log.i(TAG, "GSON created");
                            config = gson.fromJson(newConfig, Config.class);
                            Log.i(TAG, "Deserialized");
                            Log.i(TAG, "Parce to POJO");
                            //config.save(gson, share);
                            config.save(newConfig, share);
                            //allowed_protocols.clear();
                            allowed_protocols = new ArrayBlockingQueue<Protocol>(config.getParam().get(0).getAllowedTransportProtocols().size());
                            for (EnumProtocol sProto : config.getParam().get(0).getAllowedTransportProtocols()) {
                                allowed_protocols.add(EnumProtocol.createInstance(sProto));
                            }
                            Log.i(TAG, "Save config");
                            sendBroadcast(new Intent(NuubitActions.CONFIG_LOADED));
                            Log.i("System", "Config saved, mode: " + config.getParam().get(0).getOperationMode().toString());
                            if (NuubitSDK.isStatistic()) {
                                statRunner();
                            }
                        }
                    }
                }
                configuratorRunner(false);
            }
        };
    }

    private BroadcastReceiver noUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            configuratorRunner(false);
        }
    };

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
                        NuubitConstants.DEFAULT_STALE_INTERVAL
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
                        String sProtocol = bundle.getString(NuubitConstants.TEST_PROTOCOL);
                        if (!sProtocol.equalsIgnoreCase(NuubitConstants.NO_PROTOCOL)) {
                            best = EnumProtocol.createInstance(EnumProtocol.fromString(sProtocol));
                            Log.i("TEST", "Best protocol: " + best.toString());
                        } else {
                            getConfig().getParam().get(0).setOperationMode(OperationMode.report_only);
                            Log.i("TEST", "No allowed ptotocol");
                        }
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
                    HTTPCode resCode = HTTPCode.create(bundle.getInt(NuubitConstants.HTTP_RESULT, HTTPCode.UNDEFINED.getCode()));
                    String sResponce;
                    if (resCode.getType() == HTTPCode.Type.SUCCESSFULL) {
                        sResponce = bundle.getString(NuubitConstants.STATISTIC);
                    } else {
                        sResponce = resCode.getMessage();
                    }
                    Log.i(TAG + " statistic receiver", "Response: " + sResponce);
                }
                if (NuubitSDK.isStatistic()) {
                    while (statTimer != null) {
                        statTimer.cancel();
                        statTimer.purge();
                        statTimer = null;
                    }
                    statTimer = new Timer();
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
        final Intent updateIntent = new Intent(NuubitApplication.this, Configurator.class);
        updateIntent.putExtra(NuubitConstants.CONFIG, config == null ?
                NuubitConstants.DEFAULT_CONFIG_URL : config.getParam().get(0).getConfigurationApiUrl());
        updateIntent.putExtra(NuubitConstants.TIMEOUT, config == null ?
                NuubitConstants.DEFAULT_TIMEOUT_SEC : config.getParam().get(0).getConfigurationRequestTimeoutSec());
        updateIntent.putExtra(NuubitConstants.NOW, now);
        if (now) {
            startService(updateIntent);
            Log.i(TAG, updateIntent.getExtras().toString());
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
            }, NuubitApplication.getInstance().getConfig() == null ?
                    NuubitConstants.DEFAULT_CONFIG_INTERVAL
                    : NuubitApplication.getInstance().getConfig().getParam().get(0).getConfigurationRefreshIntervalSec() * 1000);
        }
    }

    private void statRunner() {
        String isStatistic = "Statistic is off";
        Log.i(TAG, String.valueOf(NuubitSDK.isStatistic()));
        if (config != null && NuubitSDK.isStatistic()) {
            Intent statIntent = new Intent(NuubitApplication.this, Statist.class);
            statIntent.putExtra(NuubitConstants.TIMEOUT,
                    config.getParam().get(0).getConfigurationStaleTimeoutSec());
            statIntent.putExtra(NuubitConstants.STATISTIC, config.getParam().get(0).getStatsReportingUrl());
            startService(statIntent);
            isStatistic = "Run statistic";
        }
        Log.i(TAG, isStatistic);
    }

    private void testerRunner() {
        Intent testIntent = new Intent(NuubitApplication.this, Tester.class);
        //ListProtocol list = NuubitApplication.getInstance().getConfig().getParam().get(0).getAllowedTransportProtocols();
        ArrayList<String> list = new ArrayList<String>();
        if (allowed_protocols != null) {
            for (Protocol proto : allowed_protocols) {
                list.add(proto.getDescription().toString());
            }
            testIntent.putStringArrayListExtra(NuubitConstants.PROTOCOLS, list);
            testIntent.putExtra(NuubitConstants.URL, getConfig().getParam().get(0).getTransportMonitoringUrl());
            startService(testIntent);
        }
    }

    private BroadcastReceiver netListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
/*
            if(intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)){
                if (allowed_protocols != null) {
                    allowed_protocols.clear();
                    for (EnumProtocol protocol : NuubitApplication.getInstance().getConfig().getParam().get(0).getAllowedTransportProtocols()) {
                        allowed_protocols.add(EnumProtocol.createInstance(protocol));
                    }
                }
            }
*/
            Log.i("TEST", "Best protocol: " + best.toString());
            Toast.makeText(NuubitApplication.getInstance(), "Best protocol: " + best.toString(), Toast.LENGTH_LONG);
            testerRunner();
        }
    };
}