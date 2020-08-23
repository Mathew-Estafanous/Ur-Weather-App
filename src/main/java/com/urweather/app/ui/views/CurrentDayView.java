package com.urweather.app.ui.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.backend.service.HourlyWeatherService;
import com.urweather.app.helpers.ImageIconHelper;
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

    private DailyWeatherService dailyWeatherService;
    private HourlyWeatherService hourlyWeatherService;

    H1 cityLocation = new H1("Richmond Hill");
    H2 currentDate = new H2("August 13, 2020");
    Image weatherIcon = new Image("frontend/weather_icons/PNG/512/day_partial_cloud.png", "Image Not Found");

    @Autowired
    public CurrentDayView(DailyWeatherService dailyWeatherService,
                            HourlyWeatherService hourlyWeatherService) {
        this.dailyWeatherService = dailyWeatherService;
        this.hourlyWeatherService = hourlyWeatherService;

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
        weatherIcon.setSrc(ImageIconHelper.getPathOfIconFromWeatherCode(hourlyWeatherService
                                                                    .getFirstHourInformation()
                                                                    .getWeatherCode()));
    }
}