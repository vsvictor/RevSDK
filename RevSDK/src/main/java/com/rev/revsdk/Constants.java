package com.rev.revsdk;

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

public class Constants {
    public static final String KEY_TAG = "com.revsdk.key";
    public static final String MAIN_CONFIG_URL = "https://sdk-config-api.revapm.net/v1/sdk/config/";

    public static final String CONFIG = "config";
    public static final String TIMEOUT = "timeout";
    public static final String TEST_PROTOCOL = "protocol";

    public static final String STATISTIC = "statistic";

    public static final String RSSI = "rssi";
    public static final String RSSI_AVERAGE = "rssi_average";
    public static final String RSSI_BEST = "rssi_best";


    public static final int DEFAULT_TIMEOUT_SEC = 10;

    public static final String SYSTEM_REQUEST = "system";
    public static final String STAT_REQUEST = "stat_request";

    public static final String HOST_HEADER_NAME = "Host";
    public static final String HOST_REV_HEADER_NAME = "X-Rev-Host";
    public static final String PROTOCOL_REV_HEADER_NAME = "X-Rev-Proto";

    public static final String undefined = "_";
    public static final String net_ip = "10.0.0.1";
    public static final String local_ip = "192.168.0.1";
    public static final String google_dns1 = "8.8.8.8";
    public static final String google_dns2 = "8.8.4.4";
    public static final int zero = 0;
    public static final String s_zero = "0";
    public static final String ip = "10.0.0.200";
    public static final String mask_24 = "255.255.255.0";
    public static final long big_number1 = 1234567891*1000;
    public static final long big_number2 = 1234567991*1000;
    public static final int hits = 0;
    public static final String level = "debug";
    public static final String rssi = "-10000";

}
