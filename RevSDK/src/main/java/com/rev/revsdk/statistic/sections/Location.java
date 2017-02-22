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

public class Location {
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
}
