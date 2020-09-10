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

    @SerializedName("temp")
    private double currentTemp;

    @SerializedName("weather_code")
    private String weatherCode;

    @SerializedName("observation_time")
    private Date hourTime;
    @SerializedName("sunrise")
    private Date sunrise;
    @SerializedName("sunset")
    private Date sunset;

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

    public Date getHourTime() {
        return hourTime;
    }

    public void setHourTime(Date hourTime) {
        this.hourTime = hourTime;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }
}