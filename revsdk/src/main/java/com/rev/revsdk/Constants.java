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
    public static final String BASE_URL = "https://sdk-config-api.revapm.net/v1/sdk/config/";
    public static final String RASE_URL = "https://sdk-config-api.revapm.net/v1/sdk/";

    public static  final int MIN_SLEEP = 200;

    public static final int DEFAULT_TIMEOUT_SEC = 10;

    public static final String HOST_HEADER_NAME = "Host";
    public static final String HOST_REV_HEADER_NAME = "X-Rev-Host";
    public static final String PROTOCOL_REV_HEADER_NAME = "X-Rev-Proto";
}
