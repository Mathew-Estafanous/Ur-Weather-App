package com.urweather.app.backend.entity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

import static com.urweather.app.helpers.ServicesConstants.LAT;
import static com.urweather.app.helpers.ServicesConstants.LON;
import static com.urweather.app.helpers.ServicesConstants.SUNRISE;
import static com.urweather.app.helpers.ServicesConstants.SUNSET;
import static com.urweather.app.helpers.ServicesConstants.CLOUD_COVER;
import static com.urweather.app.helpers.ServicesConstants.HUMIDITY;
import static com.urweather.app.helpers.ServicesConstants.VISIBILITY;
import static com.urweather.app.helpers.ServicesConstants.PRESSURE;

public class DetailWeatherEntity {

    @SerializedName(LAT) private double latitude;

    @SerializedName(LON) private double longitude;
    
    @SerializedName(SUNRISE) private Date sunrise;
    
    @SerializedName(SUNSET) private Date sunset;
    
    @SerializedName(CLOUD_COVER) private double cloudPercent;
    
    @SerializedName(HUMIDITY) private double humidityPercent;
    
    @SerializedName(VISIBILITY) private double visibility;
    
    @SerializedName(PRESSURE) private double pressure;

    public Date getSunrise() {
        return sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public double getCloudPercent() {
        return cloudPercent;
    }

    public double getHumidityPercent() {
        return humidityPercent;
    }

    public double getVisibility() {
        return visibility;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getPressure() {
        return pressure;
    }
}