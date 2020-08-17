package com.urweather.app.ui.views;

import com.urweather.app.backend.service.DailyWeatherService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CssImport("./styles/current-info-styles.css")
public class CurrentTemperatureView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    @Autowired
    private DailyWeatherService dailyWeatherService;

    H1 currentTemp = new H1("28°");
    H3 minMaxTemp = new H3("29° / 21°");

    public CurrentTemperatureView(DailyWeatherService dailyWeatherService) {
        this.dailyWeatherService = dailyWeatherService;
        addClassName("current-temperature-info-view");

        currentTemp.addClassName("current-temp");
        minMaxTemp.addClassName("minmax-temp");

        add(currentTemp, minMaxTemp);
    }

    public void updateDayTemperatureView() {
        Notification.show("UPDATED DAY'S TEMPERATURE VIEW");
    }
}