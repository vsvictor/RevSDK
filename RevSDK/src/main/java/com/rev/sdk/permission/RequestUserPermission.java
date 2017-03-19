package com.rev.sdk.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

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

/*
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
*/


public class RequestUserPermission {

    private Activity activity;
    private static final int REQUEST_STORAGE_INTERNET = 1;
    private static final int REQUEST_STORAGE_READ_PHONE_STATE = 2;
    private static final int REQUEST_STORAGE_ACCESS_NETWORK_STATE = 3;

    public RequestUserPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean verifyPermissionsInternet(PostPermissionGranted runner) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.INTERNET},
                    REQUEST_STORAGE_INTERNET);

            return false;
        } else {
            runner.onPermissionGranted();
            return true;
        }
    }

    public boolean verifyPermissionsReadPhoneState() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_STORAGE_READ_PHONE_STATE);
            return false;
        } else {
            return true;
        }

    }

    public boolean verifyPermissionsAccessNetworkState() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    REQUEST_STORAGE_ACCESS_NETWORK_STATE);
            return false;
        } else {
            return true;
        }

    }

}
