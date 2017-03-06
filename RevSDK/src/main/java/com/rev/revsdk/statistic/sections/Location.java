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

import com.rev.revsdk.utils.Pair;

import java.util.ArrayList;

public class Location extends Data implements Parcelable {
    private Context context;

    private float direction;
    private double latitude;
    private double longitude;
    private float speed;

    public Location(Context context){
        this.context = context;
        this.direction = direction();
        this.latitude = latitude();
        this.longitude = longitude();
        this.speed = speed();

    }

    protected Location(Parcel in) {
        direction = in.readFloat();
        latitude = in.readDouble();
        longitude = in.readDouble();
        speed = in.readFloat();
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("direction", String.valueOf(direction)));
        result.add(new Pair("latitude", String.valueOf(latitude)));
        result.add(new Pair("longitude", String.valueOf(longitude)));
        result.add(new Pair("speed", String.valueOf(speed)));
        return result;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    private float direction(){return 0;}
    private double latitude(){return  0;}
    private double longitude(){return 0;}
    private float speed(){return 0;}

    public float getDirection() {
        return direction;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(direction);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeFloat(speed);
    }
}
