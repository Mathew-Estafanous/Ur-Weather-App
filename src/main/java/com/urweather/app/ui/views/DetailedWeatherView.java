package com.urweather.app.ui.views;

import com.urweather.app.ui.views.reusables.DetailsSnippetLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DetailedWeatherView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    private Div sunrise = new DetailsSnippetLayout("Sunrise:", "6:49", "AM");
    private Div sunset = new DetailsSnippetLayout("Sunset:", "7:42", "PM");
    private Div cloudPercent = new DetailsSnippetLayout("Clouds:", "75", "%");
    private Div humidity = new DetailsSnippetLayout("Humidity:", "80", "%");
    private Div visibilty = new DetailsSnippetLayout("Visibility:", "10", "km");
    private Div pressure = new DetailsSnippetLayout("Pressure:", "1025", "hPa");

    public DetailedWeatherView() {
        Div mainDiv = new Div();
        mainDiv.addClassName("main-div");

        Div firstRow = new Div(sunrise, cloudPercent, visibilty);
        Div secondRow = new Div(sunset, humidity, pressure);
        firstRow.addClassName("subRow");
        secondRow.addClassName("subRow");

        mainDiv.add(firstRow, secondRow);
        add(mainDiv);
    }
}