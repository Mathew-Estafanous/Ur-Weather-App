package com.urweather.app.ui.views;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.helpers.ImageIconHelper;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.urweather.app.helpers.ConstantSymbols.DEGREE;
@Component
@UIScope
public class DailyWeatherSnippetView extends AbstractWeatherSnippetView {

    private DailyWeatherService dailyWeatherService;

    @Autowired
    public DailyWeatherSnippetView(DailyWeatherService dailyWeatherService) {
        this.dailyWeatherService = dailyWeatherService;

        listOfWeatherSnippets.addClassName("weather-snip-div");
        add(listOfWeatherSnippets);
    }

    @Override
    public void updateWeatherInformation() {
        List<DayInformationEntity> listOfDays = dailyWeatherService.getListOfDailyWeatherEntities(12);
        deleteAllCurrentSnippets();

        listOfDays.forEach(day -> {
            Date addHoursDate = day.getObservationTime();
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String time = formatter.format(addHoursDate).toUpperCase();
            String temp = Math.round(day.getMax()) + DEGREE + "/" + Math.round(day.getMin()) + DEGREE;
            String imageSrc = ImageIconHelper.getPathOfIconFromWeatherCode(day.getWeatherCode());
            addWeatherSnippet(time, imageSrc, temp);
        });
    }
}