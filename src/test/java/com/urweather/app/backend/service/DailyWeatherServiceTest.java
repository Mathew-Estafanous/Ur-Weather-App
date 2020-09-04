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
public class DailyWeatherServiceTest {

    @Autowired
    private DailyWeatherService dailyWeatherService;
    @Autowired
    private GeoLocationService geoLocationService;

    @Test
    public void dailyWeatherRepositoryIsFilledProperlly() {
        int dailyWeatherCount = dailyWeatherService.getListOfDailyWeatherEntities(6).size();
        Assert.assertNotEquals(0, dailyWeatherCount);
    }

    @Test(expected = Exception.class)
    public void failedWhenGivenNullObject() throws Exception {
        dailyWeatherService.callService(null);
        dailyWeatherService.callService(new GeoLocationObject());
    }

    @Before
    public void creatGeoLocationAndCallDailyService() {
        GeoLocationObject geoLocationObj = new GeoLocationObject();
        try {
            geoLocationService.callService("Richmond Hill, CA");
            geoLocationObj = geoLocationService.getCurrentGeoLocation();
            dailyWeatherService.callService(geoLocationObj);
        } catch (JsonSyntaxException | InputMismatchException | IndexOutOfBoundsException | IOException e) {
            Assert.fail(e.getMessage());
        }
    }
}