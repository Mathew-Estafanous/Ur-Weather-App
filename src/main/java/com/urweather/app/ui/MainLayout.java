package com.urweather.app.ui;

import com.urweather.app.ui.views.NavigationView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Ur Weather App")
@CssImport("./styles/shared-styles.css")
public class MainLayout extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    NavigationView searchBar;
    CurrentInfoLayout currentInfoLayout;
    MoreDetailInfoLayout moreDetailInfoLayout;

    public MainLayout() {
        addClassName("main-layout");
        searchBar = new NavigationView();
        currentInfoLayout = new CurrentInfoLayout();
        moreDetailInfoLayout = new MoreDetailInfoLayout();

        add(searchBar, currentInfoLayout, moreDetailInfoLayout);
    }

}