package com.urweather.app.backend.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.google.gson.annotations.SerializedName;

import static com.urweather.app.helpers.ServicesConstants.LAT;
import static com.urweather.app.helpers.ServicesConstants.LON;
import static com.urweather.app.helpers.ServicesConstants.TIME;
import static com.urweather.app.helpers.ServicesConstants.WEATHER_CODE;

@Entity
public class DayInformationEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private double min;
    private double max;

    @SerializedName(LAT)
    private double latitude;
    @SerializedName(LON)
    private double longitude;

    @SerializedName(TIME)
    private Date observationTime;
    @SerializedName(WEATHER_CODE)
    private String weatherCode;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getLat() {
        return latitude;
    }

    public void setLat(int lat) {
        this.longitude = lat;
    }

    public double getLon() {
        return longitude;
    }

    public void setLon(int lon) {
        this.longitude = lon;
    }

    public Date getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(Date observationTime) {
        this.observationTime = observationTime;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
}