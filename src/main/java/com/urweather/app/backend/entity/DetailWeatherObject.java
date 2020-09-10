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

public class DetailWeatherObject {

    @SerializedName(LAT)
    private double latitude;
    @SerializedName(LON)
    private double longitude;
    @SerializedName(SUNRISE)
    private Date sunrise;
    @SerializedName(SUNSET)
    private Date sunset;
    @SerializedName(CLOUD_COVER)
    private double cloudPercent;
    @SerializedName(HUMIDITY)
    private double humidityPercent;
    @SerializedName(VISIBILITY)
    private double visibility;
    @SerializedName(PRESSURE)
    private double pressure;

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

    public double getCloudPercent() {
        return cloudPercent;
    }

    public void setCloudPercent(double cloudPercent) {
        this.cloudPercent = cloudPercent;
    }

    public double getHumidityPercent() {
        return humidityPercent;
    }

    public void setHumidityPercent(double humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

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
}