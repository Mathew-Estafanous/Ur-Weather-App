package com.urweather.app.ui.views.differentweathertabviews;

import com.urweather.app.ui.views.IUpdatable;
import com.urweather.app.ui.views.reusables.WeatherSnippetLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class AbstractWeatherSnippetView<T> extends HorizontalLayout implements IUpdatable {
    private static final long serialVersionUID = 1L;

    Div listOfWeatherSnippets = new Div();

    protected abstract void updateWeatherInformation(T weatherInfo);

    public void addWeatherSnippet(String time, String image, String temperature) {
        WeatherSnippetLayout newWeatherSnip = new WeatherSnippetLayout(time, image, temperature);
        listOfWeatherSnippets.add(newWeatherSnip);
    }

    public void deleteAllCurrentSnippets() {
        listOfWeatherSnippets.removeAll();
    }
}