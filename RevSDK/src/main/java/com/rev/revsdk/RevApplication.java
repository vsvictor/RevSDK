package com.rev.revsdk;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rev.revsdk.config.Config;
import com.rev.revsdk.config.ConfigParamenetrs;
import com.rev.revsdk.config.ConfigsList;
import com.rev.revsdk.config.ListString;
import com.rev.revsdk.config.OperationMode;
import com.rev.revsdk.config.serialization.ConfigListDeserialize;
import com.rev.revsdk.config.serialization.ConfigListSerialize;
import com.rev.revsdk.config.serialization.ConfigParametersSerialize;
import com.rev.revsdk.config.serialization.ListStringDeserializer;
import com.rev.revsdk.config.serialization.ListStrintgSerialize;
import com.rev.revsdk.config.serialization.OperationModeDeserialize;
import com.rev.revsdk.config.serialization.OperationModeSerialize;
import com.rev.revsdk.config.serialization.TransportProtocolDeserialize;
import com.rev.revsdk.config.serialization.TransportProtocolSerialize;
import com.rev.revsdk.database.DBHelper;
import com.rev.revsdk.permission.PostPermissionGranted;
import com.rev.revsdk.permission.RequestUserPermission;
import com.rev.revsdk.protocols.ListProtocol;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.protocols.ProtocolTester;
import com.rev.revsdk.statistic.Section;
import com.rev.revsdk.statistic.sections.Carrier;
import com.rev.revsdk.statistic.Statistic;
import com.rev.revsdk.statistic.sections.Device;
import com.rev.revsdk.statistic.sections.Event;
import com.rev.revsdk.statistic.sections.Location;
import com.rev.revsdk.statistic.sections.LogEvents;
import com.rev.revsdk.statistic.sections.Network;
import com.rev.revsdk.statistic.sections.RequestOne;
import com.rev.revsdk.statistic.sections.Requests;
import com.rev.revsdk.statistic.sections.WiFi;
import com.rev.revsdk.statistic.serialize.CarrierDeserialize;
import com.rev.revsdk.statistic.serialize.CarrierSerialize;
import com.rev.revsdk.statistic.serialize.DeviceDeserialize;
import com.rev.revsdk.statistic.serialize.DeviceSerialize;
import com.rev.revsdk.statistic.serialize.EventSerialize;
import com.rev.revsdk.statistic.serialize.LocationDeserialize;
import com.rev.revsdk.statistic.serialize.LocationSerialize;
import com.rev.revsdk.statistic.serialize.LogEventsSerialize;
import com.rev.revsdk.statistic.serialize.NetworkDeserialize;
import com.rev.revsdk.statistic.serialize.NetworkSerialize;
import com.rev.revsdk.statistic.serialize.RequestOneDeserialize;
import com.rev.revsdk.statistic.serialize.RequestOneSerialize;
import com.rev.revsdk.statistic.serialize.RequestsDeserializer;
import com.rev.revsdk.statistic.serialize.RequestsSerialize;
import com.rev.revsdk.statistic.serialize.StatisticSerializer;
import com.rev.revsdk.statistic.serialize.WiFiDeserialize;
import com.rev.revsdk.statistic.serialize.WiFiSerialize;
import com.rev.revsdk.utils.Tag;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

public class RevApplication extends Application {
    private static final String TAG = RevApplication.class.getSimpleName();
    private static RevApplication instance;

    private  String sdkKey;
    private  String version;
    private  Config config;
    protected Statist statist;
    private boolean statistRunning;
    private boolean firstActivity;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private SharedPreferences share;

    private Updater updater;
    private boolean updateRunning;
    private Tester tester;
    private volatile int configRefreshInterval = 0;
    private volatile String configURL = Constants.BASE_URL;

    private String transportMonitorURL;
    private Protocol best = Protocol.STANDART;

    private DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
//        dbHelper = new DBHelper(this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
        instance = this;
        firstActivity = true;
        statistRunning = false;
        share = getSharedPreferences("RevSDK", MODE_PRIVATE);

        gsonBuilder = new GsonBuilder().registerTypeAdapter(ConfigsList.class, new ConfigListDeserialize()).registerTypeAdapter(ConfigsList.class, new ConfigListSerialize())
                .registerTypeAdapter(ConfigParamenetrs.class, new ConfigListDeserialize()).registerTypeAdapter(ConfigParamenetrs.class, new ConfigParametersSerialize())
                .registerTypeAdapter(ListString.class, new ListStringDeserializer()).registerTypeAdapter(ListString.class, new ListStrintgSerialize())
                .registerTypeAdapter(ListProtocol.class, new TransportProtocolDeserialize()).registerTypeAdapter(ListProtocol.class, new TransportProtocolSerialize())
                .registerTypeAdapter(OperationMode.class, new OperationModeDeserialize()).registerTypeAdapter(OperationMode.class, new OperationModeSerialize())
                .registerTypeAdapter(RequestOne.class, new RequestOneDeserialize()).registerTypeAdapter(RequestOne.class, new RequestOneSerialize())
                .registerTypeAdapter(Requests.class, new RequestsDeserializer()).registerTypeAdapter(Requests.class, new RequestsSerialize())
                .registerTypeAdapter(Carrier.class, new CarrierSerialize()).registerTypeAdapter(Carrier.class, new CarrierDeserialize())
                .registerTypeAdapter(Device.class, new DeviceSerialize()).registerTypeAdapter(Device.class, new DeviceDeserialize())
                .registerTypeAdapter(Event.class, new EventSerialize())
                .registerTypeAdapter(LogEvents.class, new LogEventsSerialize()).registerTypeAdapter(LogEvents.class, new LocationDeserialize())
                .registerTypeAdapter(Location.class, new LocationSerialize()).registerTypeAdapter(Location.class, new LocationDeserialize())
                .registerTypeAdapter(Network.class, new NetworkSerialize()).registerTypeAdapter(Network.class, new NetworkDeserialize())
                .registerTypeAdapter(Requests.class, new RequestsSerialize()).registerTypeAdapter(Requests.class, new RequestsDeserializer())
                .registerTypeAdapter(WiFi.class, new WiFiSerialize()).registerTypeAdapter(WiFi.class, new WiFiDeserialize())
                .registerTypeAdapter(Statistic.class, new StatisticSerializer());
        gson = gsonBuilder.create();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (firstActivity) {
                    RequestUserPermission permission = new RequestUserPermission(activity);
                    permission.verifyStoragePermissions(new PostPermissionGranted() {
                        @Override
                        public void onPermissionGranted() {
                            init();
                            firstActivity = false;
                        }

                        @Override
                        public void onPermissionDenied() {
                            firstActivity = true;
                            Toast.makeText(RevApplication.this, RevApplication.this.getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                        Class[] args = new Class[3];
                        args[0] = int.class;
                        args[1] = String[].class;
                        args[2] = int[].class;
                        Class ma = activity.getClass();
                        Method method;
                        try {
                            method = ma.getMethod("onRequestPermissionsResult", args);

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        //activity.onRequestPermissionsResult();
                    }
                    */
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                updateRunning = false;
                //updater.interrupt();
                Log.i(TAG, "Updater terminated!");
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
        //return " ";
        return result.toLowerCase();
    }

    private void init() {
        firstActivity = true;
        updateRunning = true;

        try {
            version = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            version = "1.0";
        }
        sdkKey = getKeyFromManifest();

        config = Config.load(gson, share);
        try{
            configRefreshInterval = config.getParam().get(0).getConfigurationRefreshIntervalSec();
            configURL = config.getParam().get(0).getConfigurationApiUrl();
            if(!configURL.contains("")) configURL += "config/";
            else configURL+="/";
        }catch (Exception ex){
            configRefreshInterval = 0;
            configURL = Constants.BASE_URL;
        }
        updater = new Updater();
        updater.start();
    }
    @Override
    public void onTerminate(){
        super.onTerminate();
    }
    private class Updater extends Thread{
        private long currTime;
        //private long oldTime = 0;
        @Override
        public void run() {
            while (updateRunning){
                Log.i(TAG, "Running...");
                try {
                    long currTime = System.currentTimeMillis();
                    long res = currTime - (config == null?0:config.getLastUpdate());
                    if(res>configRefreshInterval) {
                        //oldTime = currTime;
                        if(config != null) {
                            config.setLastUpdate(currTime);
                        }
                        Log.i(TAG, "Running...");
                        OkHttpClient client;
                        if(config != null && config.getParam().get(0).getConfigurationRequestTimeoutSec()>0) {
                            client = RevSDK.OkHttpCreate(config.getParam().get(0).getConfigurationRequestTimeoutSec());
                        }
                        else client = RevSDK.OkHttpCreate();

                        Request req = new Request.Builder()
                                .url(configURL + sdkKey)
                                .cacheControl(CacheControl.FORCE_NETWORK)
                                .tag(new Tag(Constants.SYSTEM_REQUEST, true))
                                .build();
                        Response response = null;
                        try {
                            response = client.newCall(req).execute();
                        } catch (IOException e) {
                            response = null;
                            e.printStackTrace();
                        }
                        try {
                            if(response.code() == 200) {
                                String result = response.body().string();

                                gson = gsonBuilder.create();

                                config = gson.fromJson(result, Config.class);
                                config.save(gson, share);
                                Log.i(TAG, "Config normal, saved\n"+result);
                                if (config != null) {
                                    transportMonitorURL = config.getParam().get(0).getTransportMonitoringUrl();
                                    configRefreshInterval = config.getParam().get(0).getConfigurationRefreshIntervalSec()*1000;
                                    config.setLastUpdate(System.currentTimeMillis());
                                    tester = new Tester();
                                    tester.start();
                                    if(!statistRunning) {
                                        statist = new Statist();
                                        statist.start();
                                        statistRunning = true;
                                    }
                                    //configRefreshInterval = 10*1000;
                                    //Log.i(TAG, "Real:"+String.valueOf(configRefreshInterval)+", Fic:"+String.valueOf(configRefreshIntervalReaf));
                                } else {
                                    //TODO No config!!!!!!!!!!!!!!!!!!!!
                                }

                            }
                            else{
                                Log.i(TAG, "Request error!!! Status code:"+String.valueOf(response.code()));
                                config = null;
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                            config = null;
                        }
                    }
                    else{
                        Log.i(TAG, "Sleep...");
                        Thread.sleep(configRefreshInterval-res);
                        Log.i(TAG, "Sleeped " + String.valueOf(configRefreshInterval-res) + "sec.");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class Tester extends Thread{
        private ProtocolTester tester;
        public Tester(){
            tester = new ProtocolTester(config.getParam().get(0).getAllowedTransportProtocols(), transportMonitorURL);
        }
        @Override
        public void run(){
            best = Protocol.STANDART;
            try {
                Log.i(TAG, "Test...");
                Thread.sleep(10000L);
                Log.i(TAG, "Tested! Best protocol: " + best.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private class Statist extends Thread{
        private Statistic statistic;
        public Statist(){
            //statistic = new Statistic(getApplicationContext());
        }
        @Override
        public void run(){
            try {
                while (statistRunning) {
                    Log.i(TAG, "Statist running...");
                    statistic = new Statistic(getApplicationContext());
                    String stat = gson.toJson(statistic);
                    Log.i(TAG, stat);
                    OkHttpClient client;
                    if(config != null && config.getParam().get(0).getConfigurationRequestTimeoutSec()>0) {
                        client = RevSDK.OkHttpCreate(config.getParam().get(0).getConfigurationRequestTimeoutSec());
                    }
                    else client = RevSDK.OkHttpCreate();

                    Request req = new Request.Builder()
                            .url(config.getParam().get(0).getStatsReportingUrl())
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .method("POST", RequestBody.create(null, new byte[0]))
                            .post(RequestBody.create(MediaType.parse("application/json"),stat))
                            .tag(new Tag(Constants.SYSTEM_REQUEST, true))
                            .build();
                    Response response = null;

                    try {
                        response = client.newCall(req).execute();
                    } catch (IOException e) {
                        response = null;
                        e.printStackTrace();
                    }
                    Log.i(TAG, response.toString());
                    Thread.sleep(5000L);
                    Log.i(TAG, "Statistic saved!!!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
}
