package com.urweather.app.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/current-info-styles.css")
public class CurrentTemperatureView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    H1 currentTemp = new H1("25°");
    H3 minMaxTemp = new H3("28° / 21°");

    public CurrentTemperatureView() {
        addClassName("current-temperature-info-view");

        currentTemp.addClassName("current-temp");
        minMaxTemp.addClassName("minmax-temp");

        add(currentTemp, minMaxTemp);
    }
}