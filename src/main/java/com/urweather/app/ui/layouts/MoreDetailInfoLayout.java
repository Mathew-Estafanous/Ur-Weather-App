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

        fillHourlyViewWithFakeData();
        fillDailyViewWithFakeData();

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

    private void addListenerForEachView() {
        searchBar.addHourlyWeatherUpdatedListener(e -> {
            hourlyWeatherView.updateWeatherInformation();
            dailyWeatherView.updateWeatherInformation();
        });
    }

    private void fillHourlyViewWithFakeData() {
        hourlyWeatherView.addWeatherSnippet("NOW", "frontend/weather_icons/PNG/256/day_clear.png", "28°");
        hourlyWeatherView.addWeatherSnippet("1 PM", "frontend/weather_icons/PNG/256/day_clear.png", "29°");
        hourlyWeatherView.addWeatherSnippet("3 PM", "frontend/weather_icons/PNG/256/day_clear.png", "29°");
        hourlyWeatherView.addWeatherSnippet("4 PM", "frontend/weather_icons/PNG/256/day_partial_cloud.png", "29°");
        hourlyWeatherView.addWeatherSnippet("5 PM", "frontend/weather_icons/PNG/256/day_partial_cloud.png", "28°");
        hourlyWeatherView.addWeatherSnippet("6 PM", "frontend/weather_icons/PNG/256/rain.png", "27°");
    }

    private void fillDailyViewWithFakeData() {
        dailyWeatherView.addWeatherSnippet("FRIDAY", "frontend/weather_icons/PNG/256/day_clear.png", "29°/18°");
        dailyWeatherView.addWeatherSnippet("SATURDAY", "frontend/weather_icons/PNG/256/day_partial_cloud.png", "27°/19°");
        dailyWeatherView.addWeatherSnippet("SUNDAY", "frontend/weather_icons/PNG/256/rain.png", "24°/17°");
        dailyWeatherView.addWeatherSnippet("MONDAY", "frontend/weather_icons/PNG/256/thunder.png", "26°/14°");
        dailyWeatherView.addWeatherSnippet("TUESDAY", "frontend/weather_icons/PNG/256/day_clear.png", "23°/13°");
        dailyWeatherView.addWeatherSnippet("WEDNESDAY", "frontend/weather_icons/PNG/256/day_clear.png", "24°/14°");
    }
}