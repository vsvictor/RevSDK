package com.rev.weather.model;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    private String id;
    @SerializedName("icon")
    private String icon;
    @SerializedName("description")
    private String description;
    @SerializedName("main")
    private String main;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "Weather [id = " + id + ", icon = " + icon + ", description = " + description + ", main = " + main + "]";
    }
}