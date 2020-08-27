package com.urweather.app.helpers;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageIconHelper {

    private static final String ROOT_ICON_PATH = "frontend/weather_icons/PNG/256/";
    private static final Map<String, String> WEATHER_ICONS = Stream.of(new String[][] {
        {"freezing_rain_heavy", "sleet.png"},
        {"freezing_rain", "sleet.png"},
        {"freezing_rain_light", "sleet.png"},
        {"freezing_drizzle", "cloudy.png"},
        {"ice_pellets_heavy", "sleet.png"},
        {"ice_pellets", "sleet.png"},
        {"ice_pellets_light", "cloud.png"},
        {"snow_heavy", "snow.png"},
        {"snow", "snow.png"},
        {"snow_light", "snow.png"},
        {"flurries", "snow.png"},
        {"tstorm", "rain_thunder.png"},
        {"rain_heavy", "rain.png"},
        {"rain", "rain.png"},
        {"rain_light", "rain.png"},
        {"drizzle", "cloudy.png"},
        {"fog_light", "fog.png"},
        {"fog", "fog.png"},
        {"cloudy", "cloudy.png"},
        {"mostly_cloudy", "overcast.png"},
        {"partly_cloudy", "day_partial_cloud.png"},
        {"mostly_clear", "day_clear.png"},
        {"clear", "day_clear.png"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<String, String> WEATHER_ICONS_NIGHT = Stream.of(new String[][] {
        {"freezing_rain_heavy", "sleet.png"},
        {"freezing_rain", "sleet.png"},
        {"freezing_rain_light", "sleet.png"},
        {"freezing_drizzle", "cloudy.png"},
        {"ice_pellets_heavy", "sleet.png"},
        {"ice_pellets", "sleet.png"},
        {"ice_pellets_light", "cloud.png"},
        {"snow_heavy", "snow.png"},
        {"snow", "snow.png"},
        {"snow_light", "snow.png"},
        {"flurries", "snow.png"},
        {"tstorm", "rain_thunder.png"},
        {"rain_heavy", "rain.png"},
        {"rain", "rain.png"},
        {"rain_light", "rain.png"},
        {"drizzle", "cloudy.png"},
        {"fog_light", "fog.png"},
        {"fog", "fog.png"},
        {"cloudy", "cloudy.png"},
        {"mostly_cloudy", "overcast.png"},
        {"partly_cloudy", "night_half_moon_partial_cloud.png"},
        {"mostly_clear", "night_half_moon_clear.png"},
        {"clear", "night_half_moon_clear.png"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static final String getPathOfIconFromWeatherCode(String weatherCode) throws NullPointerException {
        String iconPng = WEATHER_ICONS.get(weatherCode.toLowerCase());
        if(iconPng == null) {
            throw new NullPointerException("Weather Code was null. Please contact the developer");
        }
        return ROOT_ICON_PATH + iconPng;
    }

    public static final String getPathOfIconFromWeatherCodeAndTime(String weatherCode, ZonedDateTime current,
                                                ZonedDateTime sunrise, ZonedDateTime sunset) throws NullPointerException {
        String iconPng = (current.isBefore(sunset) && current.isAfter(sunrise))?
            WEATHER_ICONS.get(weatherCode.toLowerCase()) : WEATHER_ICONS_NIGHT.get(weatherCode.toLowerCase());

        if(iconPng == null) {
            throw new NullPointerException("Weather Code was null. Please contact the developer");
        }
        return ROOT_ICON_PATH + iconPng;
    }
}