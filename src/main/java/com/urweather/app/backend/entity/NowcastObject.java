package com.urweather.app.backend.entity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class NowcastObject {

    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;
    @SerializedName("temp")
    private double temperature;
    @SerializedName("weather_code")
    private String weatherCode;
    @SerializedName("observation_time")
    private Date currentDate;
    @SerializedName("sunset")
    private Date sunset;
    @SerializedName("sunrise")
    private Date sunrise;

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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }
}