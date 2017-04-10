package com.nuubit.sdk.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nuubit.sdk.services.Tester;

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

public class NetListener extends BroadcastReceiver {
    private static final String TAG = NetListener.class.getSimpleName();
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Intent testIntent = new Intent(context, Tester.class);
        context.startService(testIntent);
    }
}
