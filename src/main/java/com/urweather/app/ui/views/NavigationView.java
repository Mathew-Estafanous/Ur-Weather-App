package com.urweather.app.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.textfield.TextField;

@CssImport("./styles/shared-styles.css")
public class NavigationView extends Header {
    private static final long serialVersionUID = 1L;
    
    TextField searchField = new TextField();
    Button searchButton = new Button("Search");


    public NavigationView() {
        addClassName("navigation-bar");

        Div searchBlock = new Div();
        searchButton.addClassName("searchButton");
        searchButton.addThemeName("search-button-theme");
        searchBlock.add(searchButton);

        searchBlock.addClassName("searchBlock");
        searchField.setPlaceholder("Richmond Hill, Canada");
        searchField.setWidth("15em");
        searchField.addClassName("searchField");
        searchBlock.add(searchField);

        add(searchBlock);
    }
}