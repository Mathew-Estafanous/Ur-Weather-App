package com.urweather.app.backend.service;

import java.io.IOException;
import java.util.InputMismatchException;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationEntity;

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

    @Test
    public void dailyWeatherRepositoryIsFilledProperlly() {
        int dailyWeatherCount = dailyWeatherService.getListOfDailyWeatherEntities(12).size();
        Assert.assertNotEquals(0, dailyWeatherCount);
    }

    @Before
    public void callTheWeatherService() {
        try {
            GeoLocationEntity geoLocationObj = createMockGeoLocaionObj();
            dailyWeatherService.callService(geoLocationObj);
        } catch (JsonSyntaxException | InputMismatchException | IndexOutOfBoundsException | IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    private GeoLocationEntity createMockGeoLocaionObj() {
        GeoLocationEntity geoLocationObj = new GeoLocationEntity();
        geoLocationObj.setCity("Richmond Hill");
        geoLocationObj.setCountry("Canada");
        geoLocationObj.setCountryCode("CA");
        geoLocationObj.setLatitude(43.8828);
        geoLocationObj.setLongitude(-79.4403);
        geoLocationObj.setName("Richmond Hill");
        return geoLocationObj;
    }
}