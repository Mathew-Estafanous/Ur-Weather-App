package com.urweather.app.ui.views;

import java.text.SimpleDateFormat;
import java.util.List;

import com.urweather.app.backend.entity.HourlyInformationEntity;
import com.urweather.app.backend.service.HourlyWeatherService;
import com.urweather.app.helpers.ImageIconHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HourlyWeatherSnippetView extends AbstractWeatherSnippetView {

    private HourlyWeatherService hourlyWeatherService;

    private final String DEGREE_SYMBOL = "°";

    @Autowired
    public HourlyWeatherSnippetView(HourlyWeatherService hourlyWeatherService) {
        this.hourlyWeatherService = hourlyWeatherService;
        listOfWeatherSnippets.addClassName("weather-snip-div");
        add(listOfWeatherSnippets);
    }


    @Override
    public void updateWeatherInformation() {
        List<HourlyInformationEntity> listOfHourlyInformation = hourlyWeatherService.getListOfHourlyInformation();
        deleteAllCurrentSnippets();

        listOfHourlyInformation.forEach(hour -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh a");
            String time = dateFormat.format(hour.getHourTime()).toUpperCase();
            String temp = Math.round(hour.getCurrentTemp()) + DEGREE_SYMBOL;
            String imageSrc = ImageIconHelper.getPathOfIconFromWeatherCode(hour.getWeatherCode());
            addWeatherSnippet(time, imageSrc, temp);
        });
    }

}