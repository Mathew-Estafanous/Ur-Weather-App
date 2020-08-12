package com.urweather.app.ui;

import com.urweather.app.ui.views.CurrentDayView;
import com.urweather.app.ui.views.CurrentTemperatureView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@CssImport("./styles/current-info-styles.css")
public class CurrentInfoLayout extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    CurrentDayView currentDayView;
    CurrentTemperatureView currentTemperatureView;

    public CurrentInfoLayout() {
        addClassName("current-info");

        currentDayView = new CurrentDayView();
        currentTemperatureView = new CurrentTemperatureView();

        add(currentDayView, currentTemperatureView);
    }
}
