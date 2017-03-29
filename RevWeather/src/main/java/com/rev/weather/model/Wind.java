package com.rev.weather.model;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private String speed;
    @SerializedName("deg")
    private String degree;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Wind [speed = " + speed + ", deg = " + degree + "]";
    }
}
