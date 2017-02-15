package com.rev.revsdk;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by victor on 27.08.16.
 */
public class RequestUserPermission {

    private Activity activity;
    private static final int REQUEST_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public RequestUserPermission(Activity activity) {
        this.activity = activity;
    }

    public  void verifyStoragePermissions(PostPermissionGranted runner) {
        //int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean isPerm = true;

        for(String perm : PERMISSIONS_STORAGE){
            int permission = ActivityCompat.checkSelfPermission(activity, perm);
            isPerm = isPerm && (permission == PackageManager.PERMISSION_GRANTED);
        }
        if (!isPerm) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_STORAGE
            );
            if(runner != null) runner.onPermissionGranted();
        }
        else{
            if(runner != null) runner.onPermissionDenied();
        }
    }
}
