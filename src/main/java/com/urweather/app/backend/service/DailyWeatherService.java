package com.urweather.app.backend.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.urweather.app.backend.entity.DayInformationEntity;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.repository.DayInformationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class DailyWeatherService {

    private final String API_KEY = "jZdP0f1KuUvdEIrQPLomXIQGdutw9mI1";

    @Autowired
    private DayInformationRepository dayInformationRepository;

    public DailyWeatherService(DayInformationRepository dayInformationRepository) {
        this.dayInformationRepository = dayInformationRepository;
    }


    public final void createDailyWeatherInformation(GeoLocationObject geoLocation) throws JsonSyntaxException, IOException, NullPointerException {
        if(geoLocation == null) { throw new NullPointerException("Geo location is null!"); }
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocation);

        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        List<DayInformationEntity> listOfDayEntities = parseBodyAndReturnDayInformationEntity(responseBody, geoLocation);
        addDailyWeatherEntityToRepository(listOfDayEntities);
    }

    public List<DayInformationEntity> getListOfDailyWeatherEntities() {
        return dayInformationRepository.findAll();
    }

    public DayInformationEntity getFirstDayWeatherEntity() {
        return dayInformationRepository.findAll().get(0);
    }

    private void addDailyWeatherEntityToRepository(List<DayInformationEntity> listOfDays) {
        if(dayInformationRepository.count() != 0) {
            dayInformationRepository.deleteAll();
        }
        dayInformationRepository.saveAll(listOfDays);
    }

    private List<DayInformationEntity> parseBodyAndReturnDayInformationEntity(ResponseBody responseBody,
                                                    GeoLocationObject geoLocation) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type userType = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        List<JsonObject> unParsedDayJsonList = gson.fromJson(responseBody.string(), userType);
        return unParsedDayJsonList.stream()
                .map(day -> createDayInormationEntity(day, geoLocation))
                .collect(Collectors.toList());
    }

    private DayInformationEntity createDayInormationEntity(JsonObject dayJsonObject, GeoLocationObject geoLocationObject) {
        JsonObject dayInformationJson = new JsonObject();
        Gson gson = new Gson();
        dayInformationJson.add("lat", dayJsonObject.get("lat"));
        dayInformationJson.add("lon", dayJsonObject.get("lon"));
        dayInformationJson.add("observation_time", dayJsonObject.get("observation_time")
                                            .getAsJsonObject().get("value"));
        dayInformationJson.add("weather_code", dayJsonObject.get("weather_code")
                                            .getAsJsonObject().get("value"));
        JsonArray tempJsonArray = dayJsonObject.get("temp").getAsJsonArray();
        dayInformationJson.add("min", tempJsonArray.get(0).getAsJsonObject()
                                        .get("min").getAsJsonObject().get("value"));
        dayInformationJson.add("max", tempJsonArray.get(1).getAsJsonObject()
                                        .get("max").getAsJsonObject().get("value"));
        dayInformationJson.addProperty("city_name", geoLocationObject.getName());
        dayInformationJson.addProperty("country_code", geoLocationObject.getCountryCode());

        return gson.fromJson(dayInformationJson.toString(), DayInformationEntity.class);
    }

    private HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        return new HttpUrl.Builder().scheme("https").host("api.climacell.co").addPathSegment("v3")
            .addPathSegment("weather").addPathSegment("forecast").addPathSegment("daily")
            .addQueryParameter("lat", Double.toString(geoLocation.getLatitude()))
            .addQueryParameter("lon", Double.toString(geoLocation.getLongitude()))
            .addQueryParameter("unit_system", "si").addQueryParameter("start_time", "now")
            .addQueryParameter("fields", "temp,weather_code")
            .addQueryParameter("apikey", API_KEY);
    }
}