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
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.repository.DayInformationRepository;
import com.urweather.app.helpers.ServicesConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class DailyWeatherService extends AbstractService<GeoLocationObject, List<DayInformationEntity>, GeoLocationObject> {

    @Autowired
    private DayInformationRepository dayInformationRepo;

    public DailyWeatherService(DayInformationRepository dayInformationRepo) {
        this.dayInformationRepo = dayInformationRepo;
    }

    @Override
    public final void callService(GeoLocationObject geoLocation) throws IOException {
        if (geoLocation == null) {
            throw new NullPointerException("Geo location is null!");
        }
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocation);

        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        List<DayInformationEntity> listOfDayEntities = parseResponseBody(responseBody);
        addDailyWeatherEntityToRepository(listOfDayEntities);
    }

    @Override
    protected List<DayInformationEntity> parseResponseBody(ResponseBody responseBody)
            throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type userType = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        List<JsonObject> unParsedDayJsonList = gson.fromJson(responseBody.string(), userType);
        return unParsedDayJsonList.stream().map(day -> createDayInormationEntity(day)).collect(Collectors.toList());
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        return new HttpUrl.Builder().scheme("https").host(ServicesConstants.CLIMACELL_API_URL).addPathSegment("v3")
                .addPathSegment("weather").addPathSegment("forecast").addPathSegment("daily")
                .addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(ServicesConstants.UNIT_SYSTEM, ServicesConstants.SI)
                .addQueryParameter("start_time", "now")
                .addQueryParameter("fields", "temp,weather_code")
                .addQueryParameter("apikey", ServicesConstants.CLIMACELL_API_KEY);
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

    private DayInformationEntity createDayInormationEntity(JsonObject dayJsonObject) {
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