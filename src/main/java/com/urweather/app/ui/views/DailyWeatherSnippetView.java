package com.urweather.app.ui.views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.helpers.ImageIconHelper;
import com.urweather.app.helpers.TimezoneConvertorHelper;
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
        List<DayInformationEntity> listOfDays = dailyWeatherService.getListOfDailyWeatherEntities(6);
        deleteAllCurrentSnippets();

        listOfDays.forEach(day -> {
            Date addHoursDate = TimezoneConvertorHelper.addZoneDateHoursToGivenDate(day.getObservationTime());
            ZonedDateTime convertedDate = TimezoneConvertorHelper.convertDateToLocalTimezone(day.getLat(),
                                                day.getLon(), addHoursDate);
            DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("EE");
            String time = convertedDate.format(dateFormat).toUpperCase();
            String temp = Math.round(day.getMax()) + DEGREE + "/" + Math.round(day.getMin()) + DEGREE;
            String imageSrc = ImageIconHelper.getPathOfIconFromWeatherCode(day.getWeatherCode());
            addWeatherSnippet(time, imageSrc, temp);
        });
    }
}