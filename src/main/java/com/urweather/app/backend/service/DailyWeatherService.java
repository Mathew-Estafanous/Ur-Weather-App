package com.urweather.app.backend.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.entity.GeoLocationEntity;
import com.urweather.app.backend.repository.DayInformationRepository;
import com.urweather.app.helpers.ServicesConstants;
import com.urweather.app.helpers.APIConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

@Service
public class DailyWeatherService extends AbstractService<GeoLocationEntity, List<DayInformationEntity>> {

    @Autowired
    private final DayInformationRepository dayInformationRepo;

    public DailyWeatherService(DayInformationRepository dayInformationRepo) {
        this.dayInformationRepo = dayInformationRepo;
    }

    @Override
    protected List<DayInformationEntity> parseResponseBody(ResponseBody responseBody)
            throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type userType = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        List<JsonObject> unParsedDayJsonList = gson.fromJson(responseBody.string(), userType);
        return unParsedDayJsonList.stream()
                .map(this::createDayInformationEntity)
                .collect(Collectors.toList());
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationEntity geoLocation) {
        return new HttpUrl.Builder().scheme(APIConstants.SCHEME).host(APIConstants.CLIMACELL_API_URL)
                .addPathSegment(APIConstants.VERSION)
                .addPathSegment("weather").addPathSegment("forecast").addPathSegment("daily")
                .addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(APIConstants.UNIT_SYSTEM, APIConstants.SI)
                .addQueryParameter("start_time", "now")
                .addQueryParameter("fields", APIConstants.DAILY_FIELDS)
                .addQueryParameter("apikey", APIConstants.CLIMACELL_API_KEY);
    }

    @Override
    protected void storeEntityInChosenLocation(List<DayInformationEntity> entity) {
        addDailyWeatherEntityToRepository(entity);
    }

    public List<DayInformationEntity> getListOfDailyWeatherEntities(int total) {
        return dayInformationRepo.findAll().stream()
            .limit(total)
            .collect(Collectors.toList());
    }

    private void addDailyWeatherEntityToRepository(List<DayInformationEntity> listOfDays) {
        if (dayInformationRepo.count() != 0) {
            dayInformationRepo.deleteAll();
        }
        dayInformationRepo.saveAll(listOfDays);
    }

    private DayInformationEntity createDayInformationEntity(JsonObject dayJsonObject) {
        JsonObject dayInformationJson = new JsonObject();
        Gson gson = new Gson();
        dayInformationJson.add(ServicesConstants.LAT, dayJsonObject.get(ServicesConstants.LAT));
        dayInformationJson.add(ServicesConstants.LON, dayJsonObject.get(ServicesConstants.LON));
        dayInformationJson.add(ServicesConstants.TIME, getValueFromElement(dayJsonObject.get(ServicesConstants.TIME)));
        dayInformationJson.add(ServicesConstants.WEATHER_CODE, getValueFromElement(dayJsonObject.get(ServicesConstants.WEATHER_CODE)));
        JsonArray tempJsonArray = dayJsonObject.get(ServicesConstants.TEMPERATURE).getAsJsonArray();
        dayInformationJson.add("min", getValueFromElement(tempJsonArray.get(0).getAsJsonObject().get("min")));
        dayInformationJson.add("max", getValueFromElement(tempJsonArray.get(1).getAsJsonObject().get("max")));

        return gson.fromJson(dayInformationJson.toString(), DayInformationEntity.class);
    }
}