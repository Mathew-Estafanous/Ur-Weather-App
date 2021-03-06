package com.urweather.app.backend.entity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

import static com.urweather.app.helpers.ServicesConstants.LAT;
import static com.urweather.app.helpers.ServicesConstants.LON;
import static com.urweather.app.helpers.ServicesConstants.TEMPERATURE;
import static com.urweather.app.helpers.ServicesConstants.WEATHER_CODE;
import static com.urweather.app.helpers.ServicesConstants.TIME;
import static com.urweather.app.helpers.ServicesConstants.SUNRISE;
import static com.urweather.app.helpers.ServicesConstants.SUNSET;
public class HourlyInformationEntity {

    @SerializedName(LAT) private double latitude;

    @SerializedName(LON) private double longitude;

    @SerializedName(TEMPERATURE) private double currentTemp;

    @SerializedName(WEATHER_CODE) private String weatherCode;

    @SerializedName(TIME) private Date hourTime;

    @SerializedName(SUNRISE) private Date sunrise;

    @SerializedName(SUNSET) private Date sunset;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public Date getHourTime() {
        return hourTime;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public Date getSunset() {
        return sunset;
    }
}