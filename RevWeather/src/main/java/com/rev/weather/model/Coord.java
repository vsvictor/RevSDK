package com.rev.weather.model;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon")
    private String longitude;
    @SerializedName("lat")
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coords [lon = " + longitude + ", lat = " + latitude + "]";
    }
}
