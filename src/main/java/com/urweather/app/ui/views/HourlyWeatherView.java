package com.urweather.app.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HourlyWeatherView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    public HourlyWeatherView() {

        add(new H1("Hourly Weather HERE!"));
    }
}