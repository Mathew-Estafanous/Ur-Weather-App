package com.urweather.app.backend.entity;

import java.util.List;

import com.google.gson.JsonObject;

public class DayInformationEntity extends AbstractEntity {

    private double min;
    private double max;

    private double lat;
    private double lon;

    private List<JsonObject> temp;

    public DayInformationEntity(double min, double max, double lat, double lon, List<JsonObject> temp) {
        this.min = min;
        this.max = max;
        this.lat = lat;
        this.lon = lon;
        this.temp = temp;
    }

    public void parseTempListToTemperatureInfo() {
        this.min = temp.get(0).get("min").getAsJsonObject().get("value").getAsDouble();
        this.max = temp.get(1).get("max").getAsJsonObject().get("value").getAsDouble();
    }

    public Object getMinTemp() {
        return temp.get(0);
    }

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
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

}