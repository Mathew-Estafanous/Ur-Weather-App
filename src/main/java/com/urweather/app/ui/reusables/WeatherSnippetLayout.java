package com.urweather.app.ui.reusables;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/weather-detail-styles.css")
public class WeatherSnippetLayout extends VerticalLayout {

    private H3 time = new H3();
    private Image weatherIcon = new Image();
    private H3 temperature = new H3();

    public WeatherSnippetLayout(String time, String weatherIconString, String temperature) {
        this.time.setText(time);
        this.weatherIcon = new Image(weatherIconString, "Image Not Found");
        this.temperature.setText(temperature);

        addClassName("weather-snip-layout");
        createWeatherSnippet();
    }

    private void createWeatherSnippet() {
        time.addClassName("time-snip");
        weatherIcon.addClassName("weather-icon-snip");
        temperature.addClassName("temperature-snip");

        add(time, weatherIcon, temperature);
    }
}