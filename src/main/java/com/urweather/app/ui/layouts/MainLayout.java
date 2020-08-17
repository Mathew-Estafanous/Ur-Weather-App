package com.urweather.app.ui.layouts;

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

    @Autowired
    private NavigationView searchBar;
    @Autowired
    private CurrentInfoLayout currentInfoLayout;

    MoreDetailInfoLayout moreDetailInfoLayout;

    public MainLayout(NavigationView searchBar, CurrentInfoLayout currentInfoLayout) {
        this.searchBar = searchBar;
        this.currentInfoLayout = currentInfoLayout;
        addClassName("main-layout");
        moreDetailInfoLayout = new MoreDetailInfoLayout();

        add(searchBar, currentInfoLayout, moreDetailInfoLayout);
    }

}