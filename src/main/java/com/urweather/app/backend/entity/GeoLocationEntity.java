package com.urweather.app.backend.entity;

public class GeoLocationEntity {

    private String name;
    private String country;
    private String countryCode;
    private String city;

    private double latitude;
    private double longitude;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCity() {
        return city;
    }
}