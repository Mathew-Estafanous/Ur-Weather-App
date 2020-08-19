package com.urweather.app.ui.layouts;

import com.urweather.app.ui.views.CurrentDayView;
import com.urweather.app.ui.views.CurrentTemperatureView;
import com.urweather.app.ui.views.NavigationView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@CssImport("./styles/current-info-styles.css")
public class CurrentInfoLayout extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    private NavigationView searchBar;
    private CurrentDayView currentDayView;
    private CurrentTemperatureView currentTemperatureView;

    @Autowired
    public CurrentInfoLayout(CurrentDayView currentDayView, CurrentTemperatureView currentTemperatureView,
                                NavigationView searchBar) {
        addClassName("current-info");

        this.currentDayView = currentDayView;
        this.currentTemperatureView = currentTemperatureView;
        this.searchBar = searchBar;

        addListenerForViews();
        add(currentDayView, currentTemperatureView);
    }

    private void addListenerForViews() {
        searchBar.addTodayUpdatedListener(e -> {
            currentDayView.updateDayViewInformation();
            currentTemperatureView.updateDayTemperatureView();
        });
    }
}
