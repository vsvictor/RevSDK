package com.rev.sdk.services;

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
import android.os.Bundle;

import com.rev.sdk.Actions;
import com.rev.sdk.Constants;
import com.rev.sdk.protocols.EnumProtocol;
import com.rev.sdk.protocols.Protocol;
import com.rev.sdk.protocols.ProtocolTester;
import com.rev.sdk.types.PairLong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    protected synchronized void onHandleIntent(Intent intent) {
        Bundle b = intent.getExtras();
        if (b == null) return;
        String url = intent.getExtras().getString(Constants.URL);
        ArrayList<String> arr = intent.getStringArrayListExtra(Constants.PROTOCOLS);
        if (url == null || arr == null) return;
        List<PairLong> results = new ArrayList<PairLong>();
        for (String sProto : arr) {
            Protocol pp = EnumProtocol.createInstance(EnumProtocol.fromString(sProto));
            long pTime = pp.test(url);
            if (pTime > 0) {
                PairLong pair = new PairLong(pp.getDescription().toString(), pTime);
                results.add(pair);
            }
        }

        Collections.sort(results, new Comparator<PairLong>() {
            @Override
            public int compare(PairLong o1, PairLong o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        String result = Constants.NO_PROTOCOL;
        if (results.size() > 0) {
            long time = results.get(0).getValue();
            if (time > 0) {
                result = results.get(0).getName();
            }
        }
        Intent protocolIntent = new Intent(Actions.TEST_PROTOCOL_ACTION);
        protocolIntent.putExtra(Constants.TEST_PROTOCOL, result);
        sendBroadcast(protocolIntent);
    }
}
