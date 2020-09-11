package com.urweather.app.ui.views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.urweather.app.backend.entity.DetailWeatherEntity;
import com.urweather.app.backend.service.DetailWeatherService;
import com.urweather.app.helpers.ConstantSymbols;
import com.urweather.app.helpers.TimezoneConvertorHelper;
import com.urweather.app.ui.views.reusables.DetailsSnippetLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.stereotype.Component;

@Component
@UIScope
public class DetailedWeatherView extends HorizontalLayout {
    private static final long serialVersionUID = 1L;

    private DetailWeatherService detailWeatherService;

    private DetailsSnippetLayout sunrise = new DetailsSnippetLayout("Sunrise:", "6:49", ConstantSymbols.AM);
    private DetailsSnippetLayout sunset = new DetailsSnippetLayout("Sunset:", "7:42", ConstantSymbols.PM);
    private DetailsSnippetLayout cloudPercent = new DetailsSnippetLayout("Clouds:", "75", ConstantSymbols.PERCENT);
    private DetailsSnippetLayout humidity = new DetailsSnippetLayout("Humidity:", "80", ConstantSymbols.PERCENT);
    private DetailsSnippetLayout visibilty = new DetailsSnippetLayout("Visibility:", "10", ConstantSymbols.KM);
    private DetailsSnippetLayout pressure = new DetailsSnippetLayout("Pressure:", "1025", ConstantSymbols.HPA);

    public DetailedWeatherView(DetailWeatherService detailWeatherService) {
        this.detailWeatherService = detailWeatherService;
        Div mainDiv = new Div();
        mainDiv.addClassName("main-div");

        Div firstRow = new Div(sunrise, cloudPercent, visibilty);
        Div secondRow = new Div(sunset, humidity, pressure);
        firstRow.addClassName("subRow");
        secondRow.addClassName("subRow");

        mainDiv.add(firstRow, secondRow);
        add(mainDiv);
    }

    public void updateDetailWeatherInformation() {
        DetailWeatherEntity detailedWeather = detailWeatherService.getDetailWeatherInformation();
        ZonedDateTime convertedSunrise = TimezoneConvertorHelper.convertDateToLocalTimezone(detailedWeather.getLatitude(),
                                detailedWeather.getLongitude(), detailedWeather.getSunrise());
        ZonedDateTime convertedSunset = TimezoneConvertorHelper.convertDateToLocalTimezone(detailedWeather.getLatitude(),
                                detailedWeather.getLongitude(), detailedWeather.getSunset());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm");
        sunrise.changeValue(convertedSunrise.format(formatter));
        sunset.changeValue(convertedSunset.format(formatter));
        cloudPercent.changeValue(Long.toString(Math.round(detailedWeather.getCloudPercent())));
        humidity.changeValue(Long.toString(Math.round(detailedWeather.getHumidityPercent())));
        visibilty.changeValue(Long.toString(Math.round(detailedWeather.getVisibility())));
        pressure.changeValue(Long.toString(Math.round(detailedWeather.getPressure())));
    }
}