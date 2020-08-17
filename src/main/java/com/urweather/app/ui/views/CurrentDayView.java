package com.urweather.app.ui.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.service.DailyWeatherService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CssImport("./styles/current-info-styles.css")
public class CurrentDayView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    @Autowired
    private DailyWeatherService dailyWeatherService;

    H1 cityLocation = new H1("Richmond Hill");
    H2 currentDate = new H2("August 13, 2020");
    Image weatherIcon = new Image("frontend/weather_icons/PNG/512/day_partial_cloud.png", "Image Not Found");

    public CurrentDayView(DailyWeatherService dailyWeatherService) {
        this.dailyWeatherService = dailyWeatherService;

        addClassName("current-day-view");

        cityLocation.addClassName("city-location");
        currentDate.addClassName("current-date");

        weatherIcon.addClassName("weather-icon");

        add(cityLocation, currentDate, weatherIcon);
    }

    public void updateDayViewInformation() {
        DayInformationEntity dayInformation = dailyWeatherService.getFirstDayWeatherEntity();

        cityLocation.setText(dayInformation.getCityName());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Locale locale = new Locale("en", dayInformation.getCityName());
        SimpleDateFormat formatter = new SimpleDateFormat("MMMMM dd, yyyy", locale);
        currentDate.setText(formatter.format(calendar.getTime()));
    }
}