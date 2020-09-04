package com.urweather.app.backend.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.entity.HourlyInformationEntity;
import com.urweather.app.backend.repository.HourlyInformationRepository;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class HourlyWeatherService
        extends AbstractService<GeoLocationObject, List<HourlyInformationEntity>, GeoLocationObject> {

    private final String API_KEY = "jZdP0f1KuUvdEIrQPLomXIQGdutw9mI1";

    @Autowired
    private HourlyInformationRepository hourlyInformationRepo;

    private HourlyWeatherService(HourlyInformationRepository hourlyInformationRep) {
        this.hourlyInformationRepo = hourlyInformationRep;
    }

    @Override
    public void callService(GeoLocationObject geoLocation) throws JsonSyntaxException, IOException, NullPointerException {
        if (geoLocation == null) {
            throw new NullPointerException("Geo location is null!");
        }

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocation);

        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        List<HourlyInformationEntity> listOfHourlyEntities = parseResponseBody(responseBody);
        addHourlyInformationToRepository(listOfHourlyEntities);
    }

    @Override
    List<HourlyInformationEntity> parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type hourlyType = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        List<JsonObject> unParsedHourlyJsonObjects = gson.fromJson(responseBody.string(), hourlyType);
        return unParsedHourlyJsonObjects.stream().map(hour -> createHourlyInformationEntity(hour))
                .collect(Collectors.toList());
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        Date futureEndTime = DateUtils.addHours(new Date(), 5);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new HttpUrl.Builder().scheme("https").host("api.climacell.co").addPathSegment("v3")
                .addPathSegment("weather").addPathSegment("forecast").addPathSegment("hourly")
                .addQueryParameter("lat", Double.toString(geoLocation.getLatitude()))
                .addQueryParameter("lon", Double.toString(geoLocation.getLongitude()))
                .addQueryParameter("unit_system", "si").addQueryParameter("start_time", "now")
                .addQueryParameter("end_time", formatter.format(futureEndTime))
                .addQueryParameter("fields", "temp,weather_code,sunrise,sunset").addQueryParameter("apikey", API_KEY);
    }
    
    public HourlyInformationEntity getFirstHourInformation() {
        return hourlyInformationRepo.findAll(0);
    }

    public List<HourlyInformationEntity> getListOfHourlyInformation() {
        return hourlyInformationRepo.findAll();
    }

    private void addHourlyInformationToRepository(List<HourlyInformationEntity> listOfHourEntities) {
        if (hourlyInformationRepo.count() != 0) {
            hourlyInformationRepo.deleteAll();
        }
        hourlyInformationRepo.saveAll(listOfHourEntities);
    }

    private HourlyInformationEntity createHourlyInformationEntity(JsonObject hourJsonObject) {
        JsonObject hourInformatioObject = new JsonObject();
        Gson hourGson = new Gson();
        hourInformatioObject.add("lat", hourJsonObject.get("lat"));
        hourInformatioObject.add("lon", hourJsonObject.get("lon"));
        hourInformatioObject.add("current_temp", hourJsonObject.get("temp").getAsJsonObject().get("value"));
        hourInformatioObject.add("weather_code", hourJsonObject.get("weather_code").getAsJsonObject().get("value"));
        hourInformatioObject.add("time", hourJsonObject.get("observation_time").getAsJsonObject().get("value"));
        hourInformatioObject.add("sunset", hourJsonObject.get("sunset").getAsJsonObject().get("value"));
        hourInformatioObject.add("sunrise", hourJsonObject.get("sunrise").getAsJsonObject().get("value"));

        return hourGson.fromJson(hourInformatioObject.toString(), HourlyInformationEntity.class);
    }
}