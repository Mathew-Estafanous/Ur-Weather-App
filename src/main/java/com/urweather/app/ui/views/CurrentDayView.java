package com.urweather.app.ui.views;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/current-info-styles.css")
public class CurrentDayView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    H1 cityLocation = new H1("Richmond Hill");
    H2 currentDate = new H2("August 13, 2020");
    Image weatherIcon = new Image("frontend/weather_icons/PNG/512/day_partial_cloud.png", "Image Not Found");

    public CurrentDayView() {
        addClassName("current-day-view");

        cityLocation.addClassName("city-location");
        currentDate.addClassName("current-date");

        weatherIcon.addClassName("weather-icon");

        add(cityLocation, currentDate, weatherIcon);
    }

    public H1 getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String location) {
        this.cityLocation.setText(location);
    }

    public H2 getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String dateString) {
        this.currentDate.setText(dateString);
    }

    public Image getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(Image weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}