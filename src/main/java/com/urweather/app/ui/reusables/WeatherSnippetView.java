package com.urweather.app.ui.reusables;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WeatherSnippetView extends VerticalLayout {

    private H3 time;
    private Image weatherIcon;
    private H3 temperature;

    public WeatherSnippetView(String time, String weatherIconString, String temperature) {
        this.time.setText(time);
        this.weatherIcon = new Image(weatherIconString, "Image Not Found");
        this.temperature.setText(temperature);

        createWeatherSnippet();
    }

    private void createWeatherSnippet() {
        time.addClassName("time-snip");
        weatherIcon.addClassName("weather-icon-snip");
        temperature.addClassName("temperature-snip");

        add(time, weatherIcon, temperature);
    }
}