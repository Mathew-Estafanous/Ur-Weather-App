package com.urweather.app.ui.views;

import java.io.IOException;
import java.util.InputMismatchException;

import javax.annotation.PostConstruct;

import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationEntity;
import com.urweather.app.backend.service.AbstractService;
import com.urweather.app.backend.service.DailyWeatherService;
import com.urweather.app.backend.service.DetailWeatherService;
import com.urweather.app.backend.service.GeoLocationService;
import com.urweather.app.backend.service.HourlyWeatherService;
import com.urweather.app.backend.service.NowcastWeatherService;
import com.urweather.app.ui.events.UpdateWeatherEvent;
import com.urweather.app.ui.events.UpdateTodayWeatherEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CssImport("./styles/shared-styles.css")
@UIScope
public class NavigationView extends Header {
    private static final long serialVersionUID = 1L;

    private GeoLocationService geoLocationService;
    private DailyWeatherService dailyWeatherService;
    private HourlyWeatherService hourlyWeatherService;
    private NowcastWeatherService nowcastWeatherService;
    private DetailWeatherService detailWeatherService;

    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Div searchBlock = new Div();

    @Autowired
    public NavigationView(GeoLocationService geoLocationService, DailyWeatherService dailyWeatherService,
            HourlyWeatherService hourlyWeatherService, NowcastWeatherService nowcastWeatherService,
            DetailWeatherService detailWeatherService) {
        this.geoLocationService = geoLocationService;
        this.dailyWeatherService = dailyWeatherService;
        this.hourlyWeatherService = hourlyWeatherService;
        this.nowcastWeatherService = nowcastWeatherService;
        this.detailWeatherService = detailWeatherService;
        addClassName("navigation-bar");

        searchButton.addClassName("searchButton");
        searchButton.addThemeName("search-button-theme");
        searchBlock.add(searchButton);

        searchBlock.addClassName("searchBlock");
        searchField.setValue("Richmond Hill, CA");
        searchField.setWidth("15em");
        searchField.addClassName("searchField");
        searchBlock.add(searchField);

        addButtonEvent();
        add(searchBlock);
    }

    @PostConstruct
    public void createFirstButtonEvent() {
        searchButton.click();
    }

    private void addButtonEvent() {
        searchButton.addClickListener(event -> {
            String[] geoLocationInfo = splitStringIntoCityAndCountry(searchField.getValue());
            GeoLocationEntity geoLocation = callGeoLocationService(geoLocationInfo);
            if (geoLocation != null) {
                boolean didDailyServiceWork = callWeatherService(dailyWeatherService, geoLocation);
                boolean didHourlyServiceWork = callWeatherService(hourlyWeatherService, geoLocation);
                boolean didNowcastServiceWork = callWeatherService(nowcastWeatherService, geoLocation);
                boolean didDetailServiceWork = callWeatherService(detailWeatherService, geoLocation);

                if (didNowcastServiceWork) {
                    fireEvent(new UpdateTodayWeatherEvent(this));
                }
                if (didHourlyServiceWork && didDailyServiceWork && didDetailServiceWork) {
                    fireEvent(new UpdateWeatherEvent(this));
                }
            }
        });
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private boolean callWeatherService(AbstractService service, GeoLocationEntity geoLocation) {
        try {
            service.callService(geoLocation);
            return true;
		} catch (JsonSyntaxException | NullPointerException | IOException e) {
            Notification.show(e.getMessage());
            return false;
		}
    }

    private GeoLocationEntity callGeoLocationService(String[] location) {
        try {
            geoLocationService.callService(location);
            return geoLocationService.getCurrentGeoLocation();
        } catch (Exception e) {
            Notification.show(e.toString());
            return null;
        }
    }

    private String[] splitStringIntoCityAndCountry(String userInput) throws InputMismatchException {
        String[] splitInput = userInput.split(",");
        if(splitInput.length != 2) {
            throw new InputMismatchException("Please make sure input is in similar format to, 'Richmond Hill, CA'");
        }
        return splitInput;
    }

    public Registration addTodayUpdatedListener(ComponentEventListener<UpdateTodayWeatherEvent> listener) {
        return addListener(UpdateTodayWeatherEvent.class, listener);
    }

    public Registration addUpdateWeatherEvent(ComponentEventListener<UpdateWeatherEvent> listener) {
        return addListener(UpdateWeatherEvent.class , listener);
    }
}