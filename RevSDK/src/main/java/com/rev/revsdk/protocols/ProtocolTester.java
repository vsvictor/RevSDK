package com.rev.revsdk.protocols;

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

public class ProtocolTester extends Thread {
    private static final String TAG = ProtocolTester.class.getSimpleName();
    private ListProtocol list;
    private String url;
    private TestResult result;

    public ProtocolTester(ListProtocol protocols, String url) {
        this.list = protocols;
        this.url = url;
    }

    public void test() {
        result.clear();
        for (Protocol name : list) {
            testOne(name);
        }
    }

    public TestResult getTest() {
        return result;
    }

    private void testOne(Protocol name) {
        result.put(name, 1000L);
    }

    public Protocol getBest() {
        return Protocol.STANDART;
    }
}
