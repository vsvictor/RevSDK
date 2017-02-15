package com.rev.revsdk.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rev.revsdk.utils.NetworkUtil;

/**
 * Created by victor on 07.02.17.
 */

public class NetListener extends BroadcastReceiver {
    private static final String TAG = NetListener.class.getSimpleName();
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String status = NetworkUtil.getConnectivityStatusString(context);
        Log.e(TAG, "" + status);
        if (status.equals("Not connected to Internet")) {
            Log.e(TAG, "not connction");// your code when internet lost
        } else {
            Log.e(TAG, "connected to internet");//your code when internet connection come back
        }
    }
}
