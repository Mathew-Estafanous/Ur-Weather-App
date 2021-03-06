package com.urweather.app.backend.service;

import java.util.concurrent.CompletableFuture;

import com.urweather.app.backend.entity.GeoLocationEntity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoLocationServiceTest {

    @Autowired
    private GeoLocationService geoLocationService;

    @Test
    public void properGeoLocationObjectCreated() {
        String[] splitGeoLocation = new String[] { "Richmond Hill", "CA" };
        CompletableFuture<Boolean> result = geoLocationService.callService(splitGeoLocation);
        CompletableFuture.allOf(result).join();
        GeoLocationEntity receviedObject = geoLocationService.getCurrentGeoLocation();
        Assert.assertTrue(geoLocationServiceWorks(receviedObject));
    }


    private boolean geoLocationServiceWorks(GeoLocationEntity first) {
        return first.getCountry().equals("Canada") &&
                first.getCountryCode().equals("CA") &&
                first.getLatitude() == 43.8849 &&
                first.getLongitude() == -79.4304 &&
                first.getName().equals("Richmond Hill");
    }
}