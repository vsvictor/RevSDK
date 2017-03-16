package com.rev.revdemo.fragments.weather.model;

/**
 * Created by victor on 16.03.17.
 */

class WeatherItem {
    private long id;
    private String name;
    Coordinate coordinate;
    Main main;
    long dt;
    Wind wind;
    Sys sys;
    String rain;
    String snow;
    Clouds clouds;
    WeatherDecsription weather;
}
