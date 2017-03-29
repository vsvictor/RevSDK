package com.rev.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by victor on 29.03.17.
 */

public class Clouds {
    @SerializedName("all")
    private String all;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "Clouds [all = " + all + "]";
    }
}
