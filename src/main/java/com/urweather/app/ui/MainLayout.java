package com.urweather.app.ui;

import com.urweather.app.ui.views.NavigationView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@PageTitle("Ur Weather App")
@CssImport("./styles/shared-styles.css")
@PreserveOnRefresh
public class MainLayout extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    NavigationView searchBar;

    CurrentInfoLayout currentInfoLayout;
    MoreDetailInfoLayout moreDetailInfoLayout;

    @Autowired
    public MainLayout(NavigationView searchBar) {
        this.searchBar = searchBar;
        addClassName("main-layout");
        currentInfoLayout = new CurrentInfoLayout();
        moreDetailInfoLayout = new MoreDetailInfoLayout();

        add(searchBar, currentInfoLayout, moreDetailInfoLayout);
    }

}