package com.urweather.app.ui.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class UpdateTodayWeatherEvent extends ComponentEvent<Component> {

    public UpdateTodayWeatherEvent(Component source) {
        super(source, false);
    }
}