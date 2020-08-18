package com.urweather.app.backend.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class HourlyInformationEntity extends AbstractEntity {

    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;

    @SerializedName("current_temp")
    private double currentTemp;

    @SerializedName("weather_code")
    private String weatherCode;

    @SerializedName("time")
    private Date hourTime;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
}