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

    private NavigationView searchBar;
    private CurrentInfoLayout currentInfoLayout;
    private MoreDetailInfoLayout moreDetailInfoLayout;

    @Autowired
    public MainLayout(NavigationView searchBar,
                        CurrentInfoLayout currentInfoLayout,
                        MoreDetailInfoLayout moreDetailInfoLayout) {
        this.searchBar = searchBar;
        this.currentInfoLayout = currentInfoLayout;
        this.moreDetailInfoLayout = moreDetailInfoLayout;
        addClassName("main-layout");

        add(this.searchBar, this.currentInfoLayout, this.moreDetailInfoLayout);
    }

}