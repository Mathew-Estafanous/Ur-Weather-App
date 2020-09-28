package com.urweather.app.backend.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.urweather.app.backend.entity.GeoLocationEntity;
import com.urweather.app.backend.entity.HourlyInformationEntity;
import com.urweather.app.backend.repository.HourlyInformationRepository;
import com.urweather.app.helpers.APIConstants;
import com.urweather.app.helpers.ServicesConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

@Service
public class HourlyWeatherService extends AbstractService<GeoLocationEntity, List<HourlyInformationEntity>> {


    @Autowired
    private final HourlyInformationRepository hourlyInformationRepo;

    public HourlyWeatherService(HourlyInformationRepository hourlyInformationRep) {
        this.hourlyInformationRepo = hourlyInformationRep;
    }

    @Override
    protected List<HourlyInformationEntity> parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type hourlyType = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        List<JsonObject> unParsedHourlyJsonObjects = gson.fromJson(responseBody.string(), hourlyType);
        return unParsedHourlyJsonObjects.stream()
                .map(this::createHourlyInformationEntity)
                .collect(Collectors.toList());
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationEntity geoLocation) {
        return new HttpUrl.Builder().scheme(APIConstants.SCHEME).host(APIConstants.CLIMACELL_API_URL)
                .addPathSegment(APIConstants.VERSION)
                .addPathSegment("weather").addPathSegment("forecast").addPathSegment("hourly")
                .addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(APIConstants.UNIT_SYSTEM, APIConstants.SI)
                .addQueryParameter("start_time", "now")
                .addQueryParameter("fields", APIConstants.HOURLY_FIELDS)
                .addQueryParameter("apikey", APIConstants.CLIMACELL_API_KEY);
    }

    @Override
    protected void storeEntityInChosenLocation(List<HourlyInformationEntity> entity) {
        addHourlyInformationToRepository(entity);
    }

    public List<HourlyInformationEntity> getListOfHourlyInformation(int total) {
        return hourlyInformationRepo.findAll().stream()
                    .limit(total)
                    .collect(Collectors.toList());
    }

    private void addHourlyInformationToRepository(List<HourlyInformationEntity> listOfHourEntities) {
        if (hourlyInformationRepo.count() != 0) {
            hourlyInformationRepo.deleteAll();
        }
        hourlyInformationRepo.saveAll(listOfHourEntities);
    }

    private HourlyInformationEntity createHourlyInformationEntity(JsonObject hourJsonObject) {
        JsonObject hourInformationObject = new JsonObject();
        Gson hourGson = new Gson();
        hourInformationObject.add(ServicesConstants.LAT, hourJsonObject.get(ServicesConstants.LAT));
        hourInformationObject.add(ServicesConstants.LON, hourJsonObject.get(ServicesConstants.LON));
        hourInformationObject.add(ServicesConstants.TEMPERATURE, getValueFromElement(hourJsonObject.get(ServicesConstants.TEMPERATURE)));
        hourInformationObject.add(ServicesConstants.WEATHER_CODE, getValueFromElement(hourJsonObject.get(ServicesConstants.WEATHER_CODE)));
        hourInformationObject.add(ServicesConstants.TIME, getValueFromElement(hourJsonObject.get(ServicesConstants.TIME)));
        hourInformationObject.add(ServicesConstants.SUNSET, getValueFromElement(hourJsonObject.get(ServicesConstants.SUNSET)));
        hourInformationObject.add(ServicesConstants.SUNRISE, getValueFromElement(hourJsonObject.get(ServicesConstants.SUNRISE)));

        return hourGson.fromJson(hourInformationObject.toString(), HourlyInformationEntity.class);
    }
}