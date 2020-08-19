package com.urweather.app.ui.views;

import com.urweather.app.ui.views.reusables.WeatherSnippetLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class AbstractWeatherSnippetView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    Div listOfWeatherSnippets = new Div();

    abstract void updateWeatherInformation();

    public void addWeatherSnippet(String time, String image, String temperature) {
        WeatherSnippetLayout newWeatherSnip = new WeatherSnippetLayout(time, image, temperature);
        listOfWeatherSnippets.add(newWeatherSnip);
    }

    public void deleteAllCurrentSnippets() {
        listOfWeatherSnippets.removeAll();
    }
}