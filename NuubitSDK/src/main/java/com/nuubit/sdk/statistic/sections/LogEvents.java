package com.nuubit.sdk.statistic.sections;

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

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.nuubit.sdk.NuubitSDK;

import java.util.ArrayList;

public class LogEvents extends ArrayList<LogEvent> implements Parcelable {
    private Context context;
    public LogEvents(Context context){
        this.context = context;
    }

    protected LogEvents(Parcel in) {
        LogEvents reqs = NuubitSDK.gsonCreate().fromJson(in.readString(), LogEvents.class);
        for (LogEvent logEvent : reqs) {
            this.add(logEvent);
        }
    }

    public static final Creator<LogEvents> CREATOR = new Creator<LogEvents>() {
        @Override
        public LogEvents createFromParcel(Parcel in) {
            return new LogEvents(in);
        }

        @Override
        public LogEvents[] newArray(int size) {
            return new LogEvents[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String s = NuubitSDK.gsonCreate().toJson(this);
        dest.writeString(s);
    }
}
