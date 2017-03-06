package com.rev.revsdk.statistic.sections;

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

import com.rev.revsdk.RevSDK;

import java.util.ArrayList;

public class LogEvents extends ArrayList<Event> implements Parcelable {
    private Context context;
    public LogEvents(Context context){
        this.context = context;
    }

    protected LogEvents(Parcel in) {
        LogEvents reqs = RevSDK.gsonCreate().fromJson(in.readString(), LogEvents.class);
        for (Event event : reqs) {
            this.add(event);
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
        String s = RevSDK.gsonCreate().toJson(this);
        dest.writeString(s);
    }
}
