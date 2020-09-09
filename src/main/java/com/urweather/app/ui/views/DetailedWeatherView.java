package com.urweather.app.ui.views;

import com.urweather.app.helpers.ConstantSymbols;
import com.urweather.app.ui.views.reusables.DetailsSnippetLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DetailedWeatherView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    private Div sunrise = new DetailsSnippetLayout("Sunrise:", "6:49", ConstantSymbols.AM);
    private Div sunset = new DetailsSnippetLayout("Sunset:", "7:42", ConstantSymbols.PM);
    private Div cloudPercent = new DetailsSnippetLayout("Clouds:", "75", ConstantSymbols.PERCENT);
    private Div humidity = new DetailsSnippetLayout("Humidity:", "80", ConstantSymbols.PERCENT);
    private Div visibilty = new DetailsSnippetLayout("Visibility:", "10", ConstantSymbols.KM);
    private Div pressure = new DetailsSnippetLayout("Pressure:", "1025", ConstantSymbols.HPA);

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