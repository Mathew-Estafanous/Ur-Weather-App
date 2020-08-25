package com.urweather.app.backend.service;

import java.io.IOException;
import java.util.InputMismatchException;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;

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
        GeoLocationObject testGeoLocationObj = new GeoLocationObject();
        testGeoLocationObj.setCountry("Canada");
        testGeoLocationObj.setCountryCode("CA");
        testGeoLocationObj.setLatitude(43.8849);
        testGeoLocationObj.setLongitude(-79.4304);
        testGeoLocationObj.setName("Richmond Hill");

        GeoLocationObject receviedObject = new GeoLocationObject();
        try {
            receviedObject = geoLocationService.getGeoLocationObjFromString("Richmond Hill, CA");
        } catch (JsonSyntaxException | InputMismatchException | IndexOutOfBoundsException | IOException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(twoGeoLocationsAreEqual(testGeoLocationObj, receviedObject));
    }


    private boolean twoGeoLocationsAreEqual(GeoLocationObject first, GeoLocationObject second) {
        return first.getCountry().equals(second.getCountry()) &&
                first.getCountryCode().equals(second.getCountryCode()) &&
                first.getLatitude() == second.getLatitude() &&
                first.getLongitude() == second.getLongitude() &&
                first.getName().equals(second.getName());
    }
}