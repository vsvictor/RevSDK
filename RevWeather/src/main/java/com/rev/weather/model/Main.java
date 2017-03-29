package com.rev.weather.model;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("pressure")
    private String pressure;
    @SerializedName("temp_max")
    private String tempMax;
    @SerializedName("sea_level")
    private String seaLevel;
    @SerializedName("temp_min")
    private String tempMin;
    @SerializedName("temp")
    private String temp;
    @SerializedName("grnd_level")
    private String grndLevel;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(String seaLevel) {
        this.seaLevel = seaLevel;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(String grndLevel) {
        this.grndLevel = grndLevel;
    }

    @Override
    public String toString() {
        return "Main [humidity = " + humidity + ", pressure = " + pressure + ", tempMax = " + tempMax + ", " +
                "seaLevel = " + seaLevel + ", tempMin = " + tempMin + ", temp = " + temp + ", grndLevel = " + grndLevel + "]";
    }
}