package com.urweather.app.ui.views;

import com.urweather.app.ui.reusables.WeatherSnippetLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class WeatherSnippetView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    Div listOfWeatherSnippets = new Div();

    public WeatherSnippetView() {

        listOfWeatherSnippets.addClassName("weather-snip-div");
        add(listOfWeatherSnippets);
    }

    public void addWeatherSnippet(String time, String image, String temperature) {
        WeatherSnippetLayout newWeatherSnip = new WeatherSnippetLayout(time, image, temperature);
        listOfWeatherSnippets.add(newWeatherSnip);
    }
}