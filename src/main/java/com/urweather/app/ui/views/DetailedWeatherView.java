package com.urweather.app.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DetailedWeatherView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;
    
    public DetailedWeatherView() {
        add(new H1("Detailed Weather HERE!"));
    }
}