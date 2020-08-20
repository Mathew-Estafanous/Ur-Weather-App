package com.urweather.app.ui.views;

import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.entity.HourlyInformationEntity;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.backend.service.HourlyWeatherService;
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

    private DailyWeatherService dailyWeatherService;
    private HourlyWeatherService hourlyWeatherService;

    H1 currentTemp = new H1("28°");
    H3 minMaxTemp = new H3("29° / 21°");

    @Autowired
    public CurrentTemperatureView(DailyWeatherService dailyWeatherService,
                                    HourlyWeatherService hourlyWeatherService) {
        this.dailyWeatherService = dailyWeatherService;
        this.hourlyWeatherService = hourlyWeatherService;
        addClassName("current-temperature-info-view");

        currentTemp.addClassName("current-temp");
        minMaxTemp.addClassName("minmax-temp");

        add(currentTemp, minMaxTemp);
    }

    public void updateDayTemperatureView() {
        DayInformationEntity dayInformation = dailyWeatherService.getFirstDayWeatherEntity();
        String minTemp = Integer.toString((int) Math.round(dayInformation.getMin())) + DEGREE_SYMBOL;
        String maxTemp = Integer.toString((int) Math.round(dayInformation.getMax())) + DEGREE_SYMBOL;
        minMaxTemp.setText(maxTemp + " / " + minTemp);

        HourlyInformationEntity hourInformation = hourlyWeatherService.getFirstHourInformation();
        currentTemp.setText(Integer.toString((int) Math.round(hourInformation.getCurrentTemp())) + DEGREE_SYMBOL);
    }
}