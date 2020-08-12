package com.urweather.app.ui.views;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/current-info-styles.css")
public class CurrentDayView extends VerticalLayout {

    H1 cityLocation;
    H2 currentDate;
    Image weatherIcon;

    public CurrentDayView() {
        addClassName("current-day-view");

        cityLocation = new H1("Richmond Hill");
        cityLocation.addClassName("city-location");
        currentDate = new H2("Augus 12, 2020");
        currentDate.addClassName("current-date");

        weatherIcon = new Image("frontend/sun.png", "Image Not Found");
        weatherIcon.addClassName("weather-icon");
        weatherIcon.setWidth("300px");

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