package com.urweather.app.ui.views.reusables;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

@CssImport("./styles/weather-detail-styles.css")
public class DetailsSnippetLayout extends Div {

    private H2 detailName = new H2("Detail Name: ");
    private H2 detailValue = new H2("Value");

    private String detailUnit = "";

    public DetailsSnippetLayout(String detailName, String detailValue, String detailUnit) {
        addClassName("detail-snip-layout");
        this.detailName.addClassName("detail-name");
        this.detailValue.addClassName("detail-value");

        this.detailUnit = detailUnit;
        this.detailName.setText(detailName);
        this.detailValue.setText(detailValue + detailUnit);

        add(this.detailName, this.detailValue);
    }

    public void changeValue(String newValue) {
        detailValue.setText(newValue + detailUnit);
    }
}