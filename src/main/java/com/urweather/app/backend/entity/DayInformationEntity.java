package com.urweather.app.backend.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class DayInformationEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    
    private double min;
    private double max;

    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;

    @SerializedName("observation_time")
    private Date observationTime;
    @SerializedName("weather_code")
    private String weatherCode;

    @SerializedName("city_name")
    private String cityName;
    @SerializedName("country_code")
    private String countryCode;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}