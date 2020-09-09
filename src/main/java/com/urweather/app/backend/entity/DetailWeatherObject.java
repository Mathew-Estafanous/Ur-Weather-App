package com.urweather.app.backend.entity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class DetailWeatherObject {

    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;
    @SerializedName("sunrise")
    private Date sunrise;
    @SerializedName("sunset")
    private Date sunset;
    @SerializedName("cloud_cover")
    private double cloudPercent;
    @SerializedName("humidity")
    private double humidityPercent;
    @SerializedName("visibility")
    private double visibility;
    @SerializedName("baro_pressure")
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