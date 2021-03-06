package com.nuubit.sdk;

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

public class NuubitConstants {
    public static final String SDK_VERSION = "1";
    public static final String KEY_TAG = "com.nuubit.key";
    public static final String DEFAULT_CONFIG_URL = "https://sdk-config-api.revapm.net/v1/sdk/config/";
    public static final int DEFAULT_STALE_INTERVAL = 36000;
    //public static final int DEFAULT_CONFIG_INTERVAL = 3600;
    public static final int DEFAULT_CONFIG_INTERVAL = 60;
    public static final String DEFAULT_EDGE_HOST = "rev-200.revdn.net";
    public static final String DEFAULT_TRANSPORT_MONITORING_URL = "https://monitor.revsw.net/test-cache.js";
    public static final String DEFAULT_STATS_REPORTING_URL = "https://stats-api.revsw.net/v1/stats/apps";
    public static final int DEFAULT_STATS_REPORTING_INTERVAL = 300;
    public static final int DEFAULT_MAX_REQUEST_PER_REPORT = 500;

    public static final String HTTP_RESULT = "http_result";
    public static final String CONFIG = "config";
    public static final String TIMEOUT = "timeout";
    public static final String TEST_PROTOCOL = "protocol";
    public static final String SUCCESS_COUNT = "success_count";
    public static final String SUCCESS_TIME = "success_time";
    public static final String FAIL_COUNT = "fail_count";
    public static final String FAIL_REASON = "fail_reason";
    public static final String FAIL_TIME = "fail_time";
    public static final String NOW = "now";
    public static final String URL = "url";
    public static final String PROTOCOLS = "protocols";

    public static final String STATISTIC = "statistic";

    public static final int DEFAULT_TIMEOUT_SEC = 10;

    public static final String SYSTEM_REQUEST = "system";
    public static final String FREE_REQUEST = "free";

    public static final String USER_AGENT = "User-Agent";
    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Linux; Android 7.1.1; Nexus 6P Build/N4F26T; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/56.0.2924.87 Mobile Safari/537.36";
    public static final String HOST_HEADER_NAME = "Host";
    public static final String HOST_REV_HEADER_NAME = "X-Rev-Host";
    public static final String PROTOCOL_REV_HEADER_NAME = "X-Rev-Proto";

    public static String REV = "rev";
    public static String ORIGIN = "origin";
    public static String SYSTEM = "system";

    public static int MAX_REDIRECT = 20;

    /* Temporaty constants */
    public static final String UNDEFINED = "_";
    public static final String NET_IP = "10.0.0.1";
    public static final String LOCAL_IP = "192.168.0.1";
    public static final String GOOGLE_DNS_1 = "8.8.8.8";
    public static final String GOOGLE_DNS_2 = "8.8.4.4";
    public static final String S_ZERO = "0";
    public static final String IP = "10.0.0.200";
    public static final String MASK_24 = "255.255.255.0";
    public static final long BIG_NUMBER_1 = 1234567891000L;
    public static final long BIG_NUMBER_2 = 1234567991000L;
    public static final int HITS = 0;
    public static final String LEVEL = "debug";
    public static final String RSSI = "-10000";

    public static final String CHARGING = "Charging";
    public static final String DISCHARGING = "Discharging";
    public static final String NOT_CHARGING = "Not charging";
    public static final String FULL = "Full";

    public static final String NO_PROTOCOL = "NoProtocol";
    public static final int COUNT_PROTOCOL = 3;

    public static final int ERRORS_IN_ROW = 3;
    public static final int PENALTY_TIME_SEC = 60;

    public static final String PULL_SIZE = "pullsize";
    public static final String PULL_SUCCESS = "pullsuccess";
    public static final String PULL_FAIL = "pullfail";
    public static final String LAST_TIME_SUCCESS = "lasttimesuccess";
    public static final String LAST_TIME_FAIL = "lasttimefail";
    public static final String LAST_FAIL_REASON = "lastfailreason";
    public static final String REAL_MODE = "realmode";
    public static final String AB_ON = "abon";
    public static final String AB_MODE = "abmode";

    public static final String AVAILABLE_PROTOCOLS = "availableprotocols";
    public static final String CURRENT_PROTOCOL = "currentprotocol";

    public static final String STAT_REQUESTS_UPLOADED = "statRequestUploaded";
    public static final String STAT_REQUESTS_FAILED = "statRequestFailed";
    public static final String STAT_REQUESTS_COUNT = "requestCount";
    public static final String STAT_LAST_TIME_SUCCESS = "lastSuccessTime";
    public static final String STAT_LAST_TIME_FAIL = "lastFailTime";
    public static final String STAT_LAST_FAIL_REASON = "lastFailReason";

    public static final String STAT_NO_REQUEST = "No requests for send";

    public static final String TOTAL_REQUEST = "totalRequest";
    public static final String TOTAL_FAIL = "totalFail";
    public static final String TOTAL_KB_RECEIVED = "totalKBReceived";
    public static final String TOTAL_KB_SENT = "totalKBSent";
}

