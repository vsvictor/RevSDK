package com.nuubit.sdk.config;

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

public enum OperationMode {
    transfer_and_report,
    transfer_only,
    report_only,
    off,
    undefined;

    @Override
    public String toString(){
        String result = "UNDEFINED";
        switch (this){
            case transfer_and_report: {
                result = "transfer_and_report";
                break;
            }
            case transfer_only: {
                result = "transfer_only";
                break;
            }
            case report_only: {
                result = "report_only";
                break;
            }
            case off: {
                result = "off";
                break;
            }
        }
        return result;
    }

    public static OperationMode fromString(String mode) {
        OperationMode result = OperationMode.undefined;
        switch (mode) {
            case "transfer_and_report": {
                result = transfer_and_report;
                break;
            }
            case "transfer_only": {
                result = transfer_only;
                break;
            }
            case "report_only": {
                result = report_only;
                break;
            }
            case "off": {
                result = off;
                break;
            }
        }
        return result;
    }
}

