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

public class NowcastWeatherEntity {

    @SerializedName(LAT) private double latitude;

    @SerializedName(LON) private double longitude;

    @SerializedName(TEMPERATURE) private double temperature;

    @SerializedName(WEATHER_CODE) private String weatherCode;
    
    @SerializedName(TIME) private Date currentDate;
    
    @SerializedName(SUNSET) private Date sunset;
    
    @SerializedName(SUNRISE) private Date sunrise;  

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Date getSunset() {
        return sunset;
    }

    public Date getSunrise() {
        return sunrise;
    }
}