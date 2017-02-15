package com.rev.revsdk;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
import com.rev.revsdk.statistic.Phone;
import com.rev.revsdk.statistic.Statistic;
import com.rev.revsdk.statistic.serialize.PhoneSerialize;
import com.rev.revsdk.statistic.serialize.StatisticSerializer;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by victor on 05.02.17.
 */

public class RevApplication extends Application {
    private static final String TAG = RevApplication.class.getSimpleName();
    private  String sdkKey;
    private  String version;
    private  Config config;
    protected Statistic statistic;
    private boolean firstActivity;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private SharedPreferences share;

    private Updater updater;
    private boolean updateRunning;
    private volatile int configRefreshInterval = 0;
    private volatile String configURL = Constants.BASE_URL;

    private String transportMonitorURL;

    @Override
    public void onCreate() {
        super.onCreate();
        firstActivity = true;
        share = getSharedPreferences("RevSDK", MODE_PRIVATE);

        gsonBuilder = new GsonBuilder().registerTypeAdapter(ConfigsList.class, new ConfigListDeserialize()).registerTypeAdapter(ConfigsList.class, new ConfigListSerialize())
                .registerTypeAdapter(ConfigParamenetrs.class, new ConfigListDeserialize()).registerTypeAdapter(ConfigParamenetrs.class, new ConfigParametersSerialize())
                .registerTypeAdapter(ListString.class, new ListStringDeserializer()).registerTypeAdapter(ListString.class, new ListStrintgSerialize())
                .registerTypeAdapter(OperationMode.class, new OperationModeDeserialize()).registerTypeAdapter(OperationMode.class, new OperationModeSerialize())
                .registerTypeAdapter(Phone.class, new PhoneSerialize()).registerTypeAdapter(Statistic.class, new StatisticSerializer());
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
        }catch (NullPointerException ex){
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
                        config.setLastUpdate(currTime);
                        Log.i(TAG, "Running...");
                        OkHttpClient client;
                        if(config != null && config.getParam().get(0).getConfigurationRequestTimeoutSec()>0) {
                            client = RevSDK.OkHttpCreate(config.getParam().get(0).getConfigurationRequestTimeoutSec());
                        }
                        else client = RevSDK.OkHttpCreate();

                        Request req = new Request.Builder().url(configURL + sdkKey).cacheControl(CacheControl.FORCE_NETWORK).build();
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
}
