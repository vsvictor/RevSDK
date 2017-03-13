package com.rev.revsdk.statistic.sections;

import android.os.Parcel;
import android.os.Parcelable;

import com.rev.revsdk.types.Pair;

import java.util.ArrayList;

/**
 * Created by victor on 06.03.17.
 */

public abstract class Data implements Parcelable {
    protected Data(Parcel in) {
    }

    public Data() {

    }

    public abstract ArrayList<Pair> toArray();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
