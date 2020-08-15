package com.urweather.app.ui.views;

import java.io.IOException;
import java.util.InputMismatchException;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.service.GeoLocationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;

@CssImport("./styles/shared-styles.css")
public class NavigationView extends Header {
    private static final long serialVersionUID = 1L;

    GeoLocationService geoLocationService = new GeoLocationService();

    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Div searchBlock = new Div();

    public NavigationView() {
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
            callGeoLocationService();
        });
    }

    private void callGeoLocationService() {
        GeoLocationObject result;
        try {
            result = geoLocationService.getGeoLocationObjFromString(searchField.getValue());
        } catch (JsonSyntaxException | InputMismatchException | IOException e) {
            Notification.show(e.toString());
            return;
        }
        Notification.show(Double.toString(result.getLatitude()));
    }
}