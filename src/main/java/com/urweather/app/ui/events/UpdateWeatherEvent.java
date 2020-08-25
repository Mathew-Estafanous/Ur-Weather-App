package com.urweather.app.ui.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class UpdateWeatherEvent extends ComponentEvent<Component> {

    public UpdateWeatherEvent(Component source) {
        super(source, false);
    }
    
}