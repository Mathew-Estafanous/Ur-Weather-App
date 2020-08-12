package com.urweather.app.ui;

import com.urweather.app.ui.views.NavigationView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Ur Weather App")
public class MainLayout extends VerticalLayout {

    NavigationView searchBar;
    CurrentInfoLayout currentInfoLayout;

    public MainLayout() {
        searchBar = new NavigationView();
        currentInfoLayout = new CurrentInfoLayout();

        add(searchBar, currentInfoLayout);
    }

}