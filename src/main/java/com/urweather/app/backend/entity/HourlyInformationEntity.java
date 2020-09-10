package com.urweather.app.backend.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.google.gson.annotations.SerializedName;

import static com.urweather.app.helpers.ServicesConstants.LAT;
import static com.urweather.app.helpers.ServicesConstants.LON;
import static com.urweather.app.helpers.ServicesConstants.TEMPERATURE;
import static com.urweather.app.helpers.ServicesConstants.WEATHER_CODE;
import static com.urweather.app.helpers.ServicesConstants.TIME;
import static com.urweather.app.helpers.ServicesConstants.SUNRISE;
import static com.urweather.app.helpers.ServicesConstants.SUNSET;

@Entity
public class HourlyInformationEntity extends AbstractEntity {

    @SerializedName(LAT)
    private double latitude;
    @SerializedName(LON)
    private double longitude;

    @SerializedName(TEMPERATURE)
    private double currentTemp;

    @SerializedName(WEATHER_CODE)
    private String weatherCode;

    @SerializedName(TIME)
    private Date hourTime;
    @SerializedName(SUNRISE)
    private Date sunrise;
    @SerializedName(SUNSET)
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