package com.nuubit.sdk.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

public class DateTimeUtil {
    public static String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            //calendar.setTimeInMillis(1492194585245L);
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS z");
            Date currenTimeZone = new Date(timestamp); //(Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public static String dateToString(Context context, long date) {
        if (date <= 0) return "0";
        Date d = new Date(date);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date());
        return df.format("yyyy-MM-dd hh:mm:ss", d).toString();
    }
}
