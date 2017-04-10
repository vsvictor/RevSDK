package com.nuubit.sdk.types;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.R;

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

public enum Event {
    UNDEFINED(-1, R.string.undefined),

    START_FINISH_URL_FETCH(101, R.string.start_finish_url_fetch),
    START_FINISH_SDK_STAT_UPLOAD(102, R.string.star_finish_sdk_stats_upload_process),
    DETAIL_LOADED_SDK_CONFIG(103, R.string.details_of_loaded_sdk_configuration),
    TIMING_LOAD_OBJECT(104, R.string.timing_performance_for_loaded_objects),
    TCP_QUIC_RESTABLISHING(105, R.string.tcp_quic_connection_reestablishing),

    SDK_INIT_STAGE(201, R.string.sdk_initialization_ready_stage),
    SDK_CONFIGURATION_REFRESH(202, R.string.starting_ending_sdk_configuration_refresh),
    SDK_SELECTION_LOGIC(203, R.string.starting_ending_lm_selection_logic),
    SDK_SWITCH_MODE(204, R.string.switching_operation_mode),

    FAILED_REFRESH_CONFIG(301, R.string.failed_refresh_configuration),
    FAILED_UPLOAD_STATS(302, R.string.failed_upload_stats),
    FAILED_MONITORING_LOGIC(303, R.string.failure_monitoring_logic),
    FAILED_FETCH_OBJECT(304, R.string.failed_fetch_object),
    RECEIVED_5XX(305, R.string.received_object_with_5xx),

    SWITCH_TO_STALE_MODE(401, R.string.switching_configuration_stale_mode),
    FAILED_ALL_LM_PROTOCOL(402, R.string.failure_all_lm),
    RECOVABLE_OOM(403, R.string.recovable_oom),

    FAIL_INIT_SDK(501, R.string.fail_init_sdk),
    OS_ACCESS_PERMISSION(502, R.string.os_access_permissions),
    UNRECOVABLE_OOM(503, R.string.unrecoverable_oom);


    private int code;
    private String message;

    private Event(int code, int descriptions) {
        this.code = code;
        this.message = NuubitApplication.getInstance().getApplicationContext().getResources().getString(descriptions);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static Event create(int aCode) {
        switch (aCode) {
            case 101:
                return START_FINISH_URL_FETCH;
            case 102:
                return START_FINISH_SDK_STAT_UPLOAD;
            case 103:
                return DETAIL_LOADED_SDK_CONFIG;
            case 104:
                return TIMING_LOAD_OBJECT;
            case 105:
                return TCP_QUIC_RESTABLISHING;
            case 201:
                return SDK_INIT_STAGE;
            case 202:
                return SDK_CONFIGURATION_REFRESH;
            case 203:
                return SDK_SELECTION_LOGIC;
            case 204:
                return SDK_SWITCH_MODE;
            case 301:
                return FAILED_REFRESH_CONFIG;
            case 302:
                return FAILED_UPLOAD_STATS;
            case 303:
                return FAILED_MONITORING_LOGIC;
            case 304:
                return FAILED_FETCH_OBJECT;
            case 305:
                return RECEIVED_5XX;
            case 401:
                return SWITCH_TO_STALE_MODE;
            case 402:
                return FAILED_ALL_LM_PROTOCOL;
            case 403:
                return RECOVABLE_OOM;
            case 501:
                return FAIL_INIT_SDK;
            case 502:
                return OS_ACCESS_PERMISSION;
            case 503:
                return UNRECOVABLE_OOM;
        }
        return UNDEFINED;
    }

    public Type getType() {
        if (code < 200) return Type.DEBUG;
        else if (code >= 200 && code < 300) return Type.INFO;
        else if (code >= 300 && code < 400) return Type.WARNING;
        else if (code >= 400 && code < 500) return Type.ERROR;
        else if (code >= 500 && code < 600) return Type.CRITICAL;
        else return Type.UNDEFINED;
    }

    public static enum Type {
        DEBUG, INFO, WARNING, ERROR, CRITICAL, UNDEFINED;

        @Override
        public String toString() {
            switch (this) {
                case DEBUG: {
                    NuubitApplication.getInstance().getApplicationContext().getResources().getString(R.string.debug);
                }
                case INFO: {
                    NuubitApplication.getInstance().getApplicationContext().getResources().getString(R.string.info);
                }
                case WARNING: {
                    NuubitApplication.getInstance().getApplicationContext().getResources().getString(R.string.warning);
                }
                case ERROR: {
                    NuubitApplication.getInstance().getApplicationContext().getResources().getString(R.string.error);
                }
                case CRITICAL: {
                    NuubitApplication.getInstance().getApplicationContext().getResources().getString(R.string.critical);
                }
            }
            return NuubitApplication.getInstance().getApplicationContext().getResources().getString(R.string.undefined);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Code: ");
        builder.append(this.code);
        builder.append(" Message: ");
        builder.append(this.getMessage());
        return builder.toString();
    }

}
