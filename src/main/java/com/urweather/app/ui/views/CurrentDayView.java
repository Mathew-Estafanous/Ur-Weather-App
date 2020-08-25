package com.urweather.app.ui.views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.entity.NowcastObject;
import com.urweather.app.backend.service.GeoLocationService;
import com.urweather.app.backend.service.NowcastWeatherService;
import com.urweather.app.helpers.ImageIconHelper;
import com.urweather.app.helpers.TimezoneConvertorHelper;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
@CssImport("./styles/current-info-styles.css")
public class CurrentDayView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    private NowcastWeatherService nowcastWeatherService;
    private GeoLocationService geoLocationService;

    H1 cityLocation = new H1("Richmond Hill");
    H2 currentDate = new H2("August 13, 2020");
    Image weatherIcon = new Image("frontend/weather_icons/PNG/512/day_partial_cloud.png", "Image Not Found");

    @Autowired
    public CurrentDayView(NowcastWeatherService nowcastWeatherService, GeoLocationService geoLocationService) {
        this.nowcastWeatherService = nowcastWeatherService;
        this.geoLocationService = geoLocationService;

        addClassName("current-day-view");

        cityLocation.addClassName("city-location");
        currentDate.addClassName("current-date");

        weatherIcon.addClassName("weather-icon");

        add(cityLocation, currentDate, weatherIcon);
    }

    public void updateDayViewInformation() {
        GeoLocationObject geoLocation = geoLocationService.getCurrentGeoLocation();
        NowcastObject nowcastInformation = nowcastWeatherService.getCurreNowcastObject();

        cityLocation.setText(geoLocation.getCity());

        ZonedDateTime convertedZonedTime = TimezoneConvertorHelper.convertDateToLocalTimezone(nowcastInformation.getLatitude(),
                                                            nowcastInformation.getLongitude(), nowcastInformation.getCurrentDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        currentDate.setText(convertedZonedTime.format(formatter));
        weatherIcon.setSrc(ImageIconHelper.getPathOfIconFromWeatherCode(nowcastInformation.getWeatherCode()));
    }
}