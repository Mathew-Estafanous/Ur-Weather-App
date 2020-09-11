package com.urweather.app.ui.views;

import com.urweather.app.backend.entity.NowcastWeatherEntity;
import com.urweather.app.backend.service.NowcastWeatherService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.urweather.app.helpers.ConstantSymbols.DEGREE;

@Component
@UIScope
@CssImport("./styles/current-info-styles.css")
public class CurrentTemperatureView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    private NowcastWeatherService nowcastWeatherService;

    H1 currentTemp = new H1("28°");

    @Autowired
    public CurrentTemperatureView(NowcastWeatherService nowcastWeatherService) {
        this.nowcastWeatherService = nowcastWeatherService;
        addClassName("current-temperature-info-view");

        currentTemp.addClassName("current-temp");

        add(currentTemp);
    }

    public void updateDayTemperatureView() {
        NowcastWeatherEntity nowcastInformation = nowcastWeatherService.getCurreNowcastObject();
        currentTemp.setText(Integer.toString((int) Math.round(nowcastInformation.getTemperature())) + DEGREE);
    }
}