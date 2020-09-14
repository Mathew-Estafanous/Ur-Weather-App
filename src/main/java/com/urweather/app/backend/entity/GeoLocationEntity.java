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

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}