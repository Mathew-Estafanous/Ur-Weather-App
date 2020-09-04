package com.urweather.app.backend.service;

import java.io.IOException;
import java.util.InputMismatchException;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HourlyWeatherServiceTest {

    @Autowired
    private GeoLocationService geoLocationService;
    @Autowired
    private HourlyWeatherService hourlyWeatherService;


    @Test
    public void hourlyWeatherFilledProperly() {
        int hourlyWeatherCount = hourlyWeatherService.getListOfHourlyInformation().size();
        Assert.assertNotEquals(0, hourlyWeatherCount);
    }

    @Test(expected = Exception.class)
    public void failedWhenGivenNullObjects() throws Exception {
        hourlyWeatherService.callService(null);
        hourlyWeatherService.callService(new GeoLocationObject());
    }

    @Before
    public void createGeoLocationAndCallHourlyService() {
        GeoLocationObject geoLocationObj = new GeoLocationObject();
        try {
            geoLocationService.callService("Richmond Hill, CA");
            geoLocationObj = geoLocationService.getCurrentGeoLocation();
            hourlyWeatherService.callService(geoLocationObj);
        } catch (JsonSyntaxException | InputMismatchException | IndexOutOfBoundsException | IOException e) {
            Assert.fail(e.getMessage());
        }
    }
}