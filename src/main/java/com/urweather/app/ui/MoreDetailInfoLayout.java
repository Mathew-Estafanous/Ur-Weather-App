package com.urweather.app.ui;

import java.util.HashMap;
import java.util.Map;

import com.urweather.app.ui.views.DailyWeatherView;
import com.urweather.app.ui.views.DetailedWeatherView;
import com.urweather.app.ui.views.HourlyWeatherView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

@CssImport("./styles/weather-detail-styles.css")
public class MoreDetailInfoLayout extends VerticalLayout {

    Tabs listOfTabs = new Tabs();
    Tab hourlyTab = new Tab("Hourly");
    Tab dailyTab = new Tab("Daily");
    Tab detailsTab = new Tab("Details");

    Div listOfViews = new Div();
    HourlyWeatherView hourlyWeatherView = new HourlyWeatherView();
    DailyWeatherView dailyWeatherView = new DailyWeatherView();
    DetailedWeatherView detailedWeatherView = new DetailedWeatherView();

    public MoreDetailInfoLayout() {
        this.addClassName("more-detail-layout");

        hourlyTab.addClassName("hourly-tab");
        dailyTab.addClassName("daily-tab");
        detailsTab.addClassName("details-tab");

        listOfTabs.add(hourlyTab, dailyTab, detailsTab);
        listOfTabs.addClassName("tab-list");
        listOfTabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        listOfTabs.setSelectedTab(hourlyTab);

        // dailyWeatherView.setVisible(false);
        // detailedWeatherView.setVisible(false);
        listOfViews = new Div(hourlyWeatherView, dailyWeatherView, detailedWeatherView);
        createTabChangeFunctionality();

        add(listOfTabs, listOfViews);
    }

    private void createTabChangeFunctionality() {
        Map<Tab, Component> tabsToView = new HashMap<>();
        tabsToView.put(hourlyTab, hourlyWeatherView);
        tabsToView.put(dailyTab, dailyWeatherView);
        tabsToView.put(detailsTab, detailedWeatherView);

        listOfTabs.addSelectedChangeListener(event -> {
            tabsToView.values().forEach(view -> view.setVisible(false));
            Component selectedView = tabsToView.get(listOfTabs.getSelectedTab());
            selectedView.setVisible(true);
        });
    }
}