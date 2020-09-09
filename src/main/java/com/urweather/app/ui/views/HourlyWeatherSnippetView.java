package com.urweather.app.ui.views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.urweather.app.backend.entity.HourlyInformationEntity;
import com.urweather.app.backend.service.HourlyWeatherService;
import com.urweather.app.helpers.ConstantSymbols;
import com.urweather.app.helpers.ImageIconHelper;
import com.urweather.app.helpers.TimezoneConvertorHelper;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class HourlyWeatherSnippetView extends AbstractWeatherSnippetView {

    private HourlyWeatherService hourlyWeatherService;

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
            double hourLat = hour.getLatitude();
            double hourLong = hour.getLongitude();
            ZonedDateTime convertedCurrentTime = TimezoneConvertorHelper.convertDateToLocalTimezone(hourLat,
                                    hourLong, hour.getHourTime());
            ZonedDateTime convertedSunriseTime = TimezoneConvertorHelper.convertDateToLocalTimezone(hourLat,
                                    hourLong, hour.getSunrise());
            ZonedDateTime convertedSunsetTime = TimezoneConvertorHelper.convertDateToLocalTimezone(hourLat,
                                    hourLong, hour.getSunset());
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh a");
            String time =  convertedCurrentTime.format(dateFormat).toUpperCase();
            String temp = Math.round(hour.getCurrentTemp()) + ConstantSymbols.DEGREE;
            String imageSrc = ImageIconHelper.getPathOfIconFromWeatherCodeAndTime(hour.getWeatherCode(),
                                convertedCurrentTime, convertedSunriseTime, convertedSunsetTime);
            addWeatherSnippet(time, imageSrc, temp);
        });
    }

}