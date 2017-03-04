package com.rev.revsdk.services;

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

import android.app.IntentService;
import android.content.Intent;

import com.rev.revsdk.Actions;
import com.rev.revsdk.Constants;
import com.rev.revsdk.protocols.Protocol;
import com.rev.revsdk.protocols.ProtocolTester;

public class Tester extends IntentService {
    private static final String TAG = Tester.class.getSimpleName();
    private ProtocolTester tester;
    public Tester(){
        this("Tester");
    }
    public Tester(String name) {
        super(name);
        //tester = new ProtocolTester(config.getParam().get(0).getAllowedTransportProtocols(), transportMonitorURL);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String result = Protocol.STANDART.toString();
        Intent protocolIntent = new Intent(Actions.TEST_PROTOCOL_ACTION);
        protocolIntent.putExtra(Constants.TEST_PROTOCOL, result);
        sendBroadcast(protocolIntent);
    }
}
