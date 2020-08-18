package com.urweather.app.ui.views;

import java.io.IOException;
import java.util.InputMismatchException;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.backend.service.GeoLocationService;
import com.urweather.app.backend.service.HourlyWeatherService;
import com.urweather.app.ui.events.UpdateTodayWeatherEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CssImport("./styles/shared-styles.css")
public class NavigationView extends Header {
    private static final long serialVersionUID = 1L;

    @Autowired
    private GeoLocationService geoLocationService;
    @Autowired
    private DailyWeatherService dailyWeatherService;
    @Autowired
    private HourlyWeatherService hourlyWeatherService;

    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Div searchBlock = new Div();

    public NavigationView(GeoLocationService geoLocationService,
                            DailyWeatherService dailyWeatherService,
                            HourlyWeatherService hourlyWeatherService) {
        this.geoLocationService = geoLocationService;
        this.dailyWeatherService = dailyWeatherService;
        this.hourlyWeatherService = hourlyWeatherService;
        addClassName("navigation-bar");

        searchButton.addClassName("searchButton");
        searchButton.addThemeName("search-button-theme");
        searchBlock.add(searchButton);

        searchBlock.addClassName("searchBlock");
        searchField.setPlaceholder("Richmond Hill, Canada");
        searchField.setWidth("15em");
        searchField.addClassName("searchField");
        searchBlock.add(searchField);

        addButtonEvent();
        add(searchBlock);
    }

    private void addButtonEvent() {
        searchButton.addClickListener(event -> {
            GeoLocationObject geoLocation = callGeoLocationService();
            boolean didDailyServiceWork = callDailyWeatherService(geoLocation);
            boolean didHourlyServiceWork = callHourlyWeatherService(geoLocation);

            if(didDailyServiceWork) {
                fireEvent(new UpdateTodayWeatherEvent(this));
            }
            if(didHourlyServiceWork) {
                Notification.show("HOURLY SERVICE WORKED!!!");
            }
        });
    }

    private boolean callHourlyWeatherService(GeoLocationObject geoLocation) {
        try {
            hourlyWeatherService.createHourlyWeatherInformation(geoLocation);
            return true;
        } catch (IOException | NullPointerException e) {
            Notification.show(e.getMessage());
            return false;
        }
    }

    private boolean callDailyWeatherService(GeoLocationObject geoLocation) {
        try {
            dailyWeatherService.createDailyWeatherInformation(geoLocation);
            return true;
        } catch (JsonSyntaxException | IOException e) {
            Notification.show(e.getMessage());
            return false;
        }
    }

    private GeoLocationObject callGeoLocationService() {
        GeoLocationObject geoLocation = new GeoLocationObject();
        try {
            geoLocation = geoLocationService.getGeoLocationObjFromString(searchField.getValue());
        } catch (JsonSyntaxException | InputMismatchException | IOException e) {
            Notification.show(e.toString());
        }
        return geoLocation;
    }

    public Registration addTodayUpdatedListener(ComponentEventListener<UpdateTodayWeatherEvent> listener) {
        return addListener(UpdateTodayWeatherEvent.class, listener);
    }
}