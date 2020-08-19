package com.urweather.app.ui.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class UpdateHourlyWeatherEvent extends ComponentEvent<Component> {

    public UpdateHourlyWeatherEvent(Component source) {
        super(source, false);
    }
    
}