package com.urweather.app.ui.layouts;

import com.urweather.app.ui.views.CurrentDayView;
import com.urweather.app.ui.views.CurrentTemperatureView;
import com.urweather.app.ui.views.NavigationView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.springframework.stereotype.Component;


@Component
@CssImport("./styles/current-info-styles.css")
public class CurrentInfoLayout extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    NavigationView searchBar;
    CurrentDayView currentDayView;
    CurrentTemperatureView currentTemperatureView;

    public CurrentInfoLayout(CurrentDayView currentDayView, CurrentTemperatureView currentTemperatureView,
                                NavigationView searchBar) {
        addClassName("current-info");

        this.currentDayView = currentDayView;
        this.currentTemperatureView = currentTemperatureView;
        this.searchBar = searchBar;

        addListener();
        add(currentDayView, currentTemperatureView);
    }

    private void addListener() {
        searchBar.addTodayUpdatedListener(e -> {
            currentDayView.updateDayViewInformation();
            currentTemperatureView.updateDayTemperatureView();
        });
    }
}
