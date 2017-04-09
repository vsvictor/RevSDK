package com.rev.weather.model;

import com.google.gson.annotations.SerializedName;

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

public class Root {
    @SerializedName("id")
    private String id;
    @SerializedName("dt")
    private String dt;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("coord")
    private Coord coord;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("cod")
    private String code;
    @SerializedName("sys")
    private Sys system;
    @SerializedName("name")
    private String nameCity;
    @SerializedName("base")
    private String base;
    @SerializedName("weather")
    private Weather[] weather;
    @SerializedName("main")
    private Main main;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getDT() {
        return dt;
    }

    public void setDT(String dt) {
        this.dt = dt;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sys getSystem() {
        return system;
    }

    public void setSystem(Sys system) {
        this.system = system;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < weather.length; i++) {
            builder.append(getWeather()[i]);
        }
        return "Root [id = " + id + ", dt = " + dt + ", clouds = " + clouds + ", coord = " + coord + ", " +
                "wind = " + wind + ", cod = " + code + ", sys = " + system + ", name = " + nameCity +
                ", base = " + base + ", main = " + main + ", " + builder.toString() + "]";
    }
}
