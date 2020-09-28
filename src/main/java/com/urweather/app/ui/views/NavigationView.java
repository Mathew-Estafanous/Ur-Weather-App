package com.urweather.app.ui.views;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

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

    private final GeoLocationService geoLocationService;
    private final DailyWeatherService dailyWeatherService;
    private final HourlyWeatherService hourlyWeatherService;
    private final NowcastWeatherService nowcastWeatherService;
    private final DetailWeatherService detailWeatherService;

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
    public void startByCallingServiceSearch() {
        searchButton.click();
    }

    private void addButtonEvent() {
        searchButton.addClickListener(event -> runSearchWhenClicked());
    }

    private void runSearchWhenClicked() {
        Optional<String[]> splitLocation = splitStringIntoCityAndCountry(searchField.getValue());
        if(splitLocation.isEmpty()) return;

        Optional<GeoLocationEntity> geoLocation = callGeoLocationService(splitLocation.get());
        geoLocation.ifPresent(location -> {
            boolean allWorked = callAllWeatherServices(location);

            if (allWorked) {
                fireEvent(new UpdateTodayWeatherEvent(this));
                fireEvent(new UpdateWeatherEvent(this));
            }
        });
    }

    private boolean callAllWeatherServices(GeoLocationEntity geoLocation) {
        CompletableFuture<Boolean> didDailyServiceWork = callWeatherService(dailyWeatherService, geoLocation);
        CompletableFuture<Boolean> didHourlyServiceWork = callWeatherService(hourlyWeatherService, geoLocation);
        CompletableFuture<Boolean> didNowcastServiceWork = callWeatherService(nowcastWeatherService, geoLocation);
        CompletableFuture<Boolean> didDetailServiceWork = callWeatherService(detailWeatherService, geoLocation);

        CompletableFuture.allOf(didDailyServiceWork, didHourlyServiceWork, didNowcastServiceWork, didDetailServiceWork)
                .join();

        try {
            return didDailyServiceWork.get() && didHourlyServiceWork.get() && didNowcastServiceWork.get()
                    && didDailyServiceWork.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CompletableFuture<Boolean> callWeatherService(AbstractService service, GeoLocationEntity geoLocation) {
        return service.callService(geoLocation);
    }

    private Optional<GeoLocationEntity> callGeoLocationService(String[] location) {
        CompletableFuture<Boolean> didGeoServiceWork = geoLocationService.callService(location);
        CompletableFuture.allOf(didGeoServiceWork).join();
        return Optional.of(geoLocationService.getCurrentGeoLocation());
    }

    private Optional<String[]> splitStringIntoCityAndCountry(String userInput) {
        String[] splitInput = userInput.split(",");
        if(splitInput.length != 2) {
            Notification.show("Please make sure input is in similar format to, 'Richmond Hill, CA'");
            return Optional.empty();
        }
        return Optional.of(splitInput);
    }

    public Registration addTodayUpdatedListener(ComponentEventListener<UpdateTodayWeatherEvent> listener) {
        return addListener(UpdateTodayWeatherEvent.class, listener);
    }

    public Registration addUpdateWeatherEvent(ComponentEventListener<UpdateWeatherEvent> listener) {
        return addListener(UpdateWeatherEvent.class , listener);
    }
}