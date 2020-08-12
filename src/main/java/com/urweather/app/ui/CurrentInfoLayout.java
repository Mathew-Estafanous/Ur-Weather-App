package com.urweather.app.ui;

import com.urweather.app.ui.views.CurrentDayView;
import com.urweather.app.ui.views.CurrentTemperatureView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@CssImport("./styles/shared-styles.css")
public class CurrentInfoLayout extends HorizontalLayout {

    CurrentDayView currentDayView;
    CurrentTemperatureView currentTemperatureView;

    public CurrentInfoLayout() {
        addClassName("current-info");
        setSizeFull();

        currentDayView = new CurrentDayView();
        currentTemperatureView = new CurrentTemperatureView();

        add(currentDayView, currentTemperatureView);
    }
}
