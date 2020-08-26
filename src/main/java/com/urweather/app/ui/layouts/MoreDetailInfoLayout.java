package com.urweather.app.ui.layouts;

import java.util.HashMap;
import java.util.Map;

import com.urweather.app.ui.views.DailyWeatherSnippetView;
import com.urweather.app.ui.views.DetailedWeatherView;
import com.urweather.app.ui.views.HourlyWeatherSnippetView;
import com.urweather.app.ui.views.NavigationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component
@UIScope
@CssImport("./styles/weather-detail-styles.css")
public class MoreDetailInfoLayout extends VerticalLayout {

    private NavigationView searchBar;

    Tabs listOfTabs = new Tabs();
    Tab hourlyTab = new Tab("Hourly");
    Tab dailyTab = new Tab("Daily");
    Tab detailsTab = new Tab("Details");

    Div listOfViews = new Div();
    HourlyWeatherSnippetView hourlyWeatherView;
    DailyWeatherSnippetView dailyWeatherView;
    DetailedWeatherView detailedWeatherView = new DetailedWeatherView();

    @Autowired
    public MoreDetailInfoLayout(NavigationView searchBar, DailyWeatherSnippetView dailyWeatherView,
                                    HourlyWeatherSnippetView hourlyWeatherSnippetView) {
        this.searchBar = searchBar;
        this.dailyWeatherView = dailyWeatherView;
        this.hourlyWeatherView = hourlyWeatherSnippetView;
        addClassName("more-detail-layout");

        hourlyTab.addClassName("hourly-tab");
        dailyTab.addClassName("daily-tab");
        detailsTab.addClassName("details-tab");

        listOfTabs.add(hourlyTab, dailyTab, detailsTab);
        listOfTabs.addClassName("tab-list");
        listOfTabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        listOfTabs.setSelectedTab(hourlyTab);

        dailyWeatherView.setVisible(false);
        detailedWeatherView.setVisible(false);
        listOfViews = new Div(hourlyWeatherView, dailyWeatherView, detailedWeatherView);
        listOfViews.addClassName("list-of-views");
        createTabChangeFunctionality();
        addListenerForEachView();

        add(listOfTabs, listOfViews);
        startUpUIWithBaseCity();
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

    private void startUpUIWithBaseCity() {
        hourlyWeatherView.updateWeatherInformation();
        dailyWeatherView.updateWeatherInformation();
    }

    private void addListenerForEachView() {
        searchBar.addUpdateWeatherEvent(e -> {
            hourlyWeatherView.updateWeatherInformation();
            dailyWeatherView.updateWeatherInformation();
        });
    }
}