package com.urweather.app.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

@CssImport("./styles/shared-styles.css")
public class NavigationView extends Header {

    TextField searchField = new TextField();
    Button searchButton = new Button("Search");


    public NavigationView() {
        addClassName("navigation-bar");
        setSizeFull();

        Div searchBlock = new Div();
        searchButton.addClassName("searchButton");
        searchBlock.add(searchButton);

        searchBlock.addClassName("searchBlock");
        searchField.setPlaceholder("Richmond Hill, Canada");
        searchField.setWidth("15em");
        searchField.addClassName("searchField");
        searchBlock.add(searchField);

        add(searchBlock);
    }
}