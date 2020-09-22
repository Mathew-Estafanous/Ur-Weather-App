package com.urweather.app.backend.service;

import java.util.concurrent.CompletableFuture;

import com.urweather.app.backend.entity.GeoLocationEntity;
import com.urweather.app.backend.entity.NowcastWeatherEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NowcastWeatherServiceTest {

    @Autowired
    private NowcastWeatherService nowcastWeatherService;

    @Test
    public void getNowcastWeatherGetsNonNullObject() {
        NowcastWeatherEntity nowcastWeatherEntity = nowcastWeatherService.getCurrentNowcastObject();
        Assert.assertNotNull(nowcastWeatherEntity);
    }

    @Before
    public void callTheWeatherService() {
        GeoLocationEntity geoLocationObj = createMockGeoLocaionObj();
        CompletableFuture<Boolean> result = nowcastWeatherService.callService(geoLocationObj);
        CompletableFuture.allOf(result).join();
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