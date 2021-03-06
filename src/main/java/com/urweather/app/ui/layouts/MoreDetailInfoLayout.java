package com.urweather.app.ui.layouts;

import java.util.HashMap;
import java.util.Map;

import com.urweather.app.ui.views.differentweathertabviews.DailyWeatherSnippetView;
import com.urweather.app.ui.views.differentweathertabviews.DetailedWeatherView;
import com.urweather.app.ui.views.differentweathertabviews.HourlyWeatherSnippetView;
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

    private Tabs listOfTabs = new Tabs();
    private Tab hourlyTab = new Tab("Hourly");
    private Tab dailyTab = new Tab("Daily");
    private Tab detailsTab = new Tab("Details");

    private Div listOfViews = new Div();

    private final HourlyWeatherSnippetView hourlyWeatherView;
    private final DailyWeatherSnippetView dailyWeatherView;
    private final DetailedWeatherView detailedWeatherView;

    @Autowired
    public MoreDetailInfoLayout(NavigationView searchBar, DailyWeatherSnippetView dailyWeatherView,
                    HourlyWeatherSnippetView hourlyWeatherSnippetView, DetailedWeatherView detailedWeatherView) {
        this.searchBar = searchBar;
        this.dailyWeatherView = dailyWeatherView;
        this.hourlyWeatherView = hourlyWeatherSnippetView;
        this.detailedWeatherView = detailedWeatherView;
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
        hourlyWeatherView.updateWeatherView();
        dailyWeatherView.updateWeatherView();
        detailedWeatherView.updateWeatherView();
    }

    private void addListenerForEachView() {
        searchBar.addUpdateWeatherEvent(e -> {
            startUpUIWithBaseCity();
        });
    }
}