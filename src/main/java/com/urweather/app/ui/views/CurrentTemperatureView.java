package com.urweather.app.ui.views;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.entity.NowcastObject;
import com.urweather.app.backend.service.GeoLocationService;
import com.urweather.app.backend.service.NowcastWeatherService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
@CssImport("./styles/current-info-styles.css")
public class CurrentTemperatureView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    private final String DEGREE_SYMBOL = "°";

    private NowcastWeatherService nowcastWeatherService;
    private GeoLocationService geoLocationService;

    H1 currentTemp = new H1("28°");

    @Autowired
    public CurrentTemperatureView(NowcastWeatherService nowcastWeatherService, GeoLocationService geoLocationService) {
        this.nowcastWeatherService = nowcastWeatherService;
        this.geoLocationService = geoLocationService;
        addClassName("current-temperature-info-view");

        currentTemp.addClassName("current-temp");

        add(currentTemp);
    }

    public void updateDayTemperatureView() {

        GeoLocationObject geoLocaion = geoLocationService.getCurrentGeoLocation();
        NowcastObject nowcastInformation;
        try {
            nowcastInformation = nowcastWeatherService.getNowcastObjectFromGeoLocation(geoLocaion);
        } catch (JsonSyntaxException | IOException e) {
            return;
        }
        currentTemp.setText(Integer.toString((int) Math.round(nowcastInformation.getTemperature())) + DEGREE_SYMBOL);
    }
}