package com.urweather.app.backend.entity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

import static com.urweather.app.helpers.ServicesConstants.LAT;
import static com.urweather.app.helpers.ServicesConstants.LON;
import static com.urweather.app.helpers.ServicesConstants.TIME;
import static com.urweather.app.helpers.ServicesConstants.WEATHER_CODE;

public class DayInformationEntity {

    private double min;
    private double max;

    @SerializedName(LAT) private double latitude;

    @SerializedName(LON) private double longitude;

    @SerializedName(TIME) private Date observationTime;

    @SerializedName(WEATHER_CODE) private String weatherCode;

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getLat() {
        return latitude;
    }

    public double getLon() {
        return longitude;
    }

    public Date getObservationTime() {
        return observationTime;
    }

    public String getWeatherCode() {
        return weatherCode;
    }
}