package com.rev.revsdk.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;

import com.rev.revsdk.permission.PostPermissionGranted;

import java.util.ArrayList;
import java.util.List;

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
public class RequestUserPermission {

    private Activity activity;
    private static final int REQUEST_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
/*
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
*/
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
    public static List<PermissionInfo> getAllPermissions(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PermissionInfo> permissions = new ArrayList<>();

        List<PermissionGroupInfo> groupList = pm.getAllPermissionGroups(0);
        groupList.add(null); // ungrouped permissions

        for (PermissionGroupInfo permissionGroup : groupList) {
            String name = permissionGroup == null ? null : permissionGroup.name;
            try {
                permissions.addAll(pm.queryPermissionsByGroup(name, 0));
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }

        return permissions;
    }
}
