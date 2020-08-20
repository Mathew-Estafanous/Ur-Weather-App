package com.urweather.app.ui.views;

import java.text.SimpleDateFormat;
import java.util.List;

import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.helpers.ImageIconHelper;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class DailyWeatherSnippetView extends AbstractWeatherSnippetView {

    private DailyWeatherService dailyWeatherService;

    private final String DEGREE_SYMBOL = "°";

    @Autowired
    public DailyWeatherSnippetView(DailyWeatherService dailyWeatherService) {
        this.dailyWeatherService = dailyWeatherService;

        listOfWeatherSnippets.addClassName("weather-snip-div");
        add(listOfWeatherSnippets);
    }

    @Override
    public void updateWeatherInformation() {
        List<DayInformationEntity> listOfDays = dailyWeatherService.getListOfDailyWeatherEntities(6);
        deleteAllCurrentSnippets();

        listOfDays.forEach(day -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE");
            String time = dateFormat.format(day.getObservationTime()).toUpperCase();
            String temp = Math.round(day.getMax()) + DEGREE_SYMBOL + "/" + Math.round(day.getMin()) + DEGREE_SYMBOL;
            String imageSrc = ImageIconHelper.getPathOfIconFromWeatherCode(day.getWeatherCode());
            addWeatherSnippet(time, imageSrc, temp);
        });
    }
}