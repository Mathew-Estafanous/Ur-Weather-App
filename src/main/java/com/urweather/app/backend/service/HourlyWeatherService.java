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
import com.urweather.app.helpers.APIConstants;
import com.urweather.app.helpers.ServicesConstants;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

@Service
public class HourlyWeatherService
        extends AbstractService<GeoLocationObject, List<HourlyInformationEntity>, GeoLocationObject> {


    @Autowired
    private HourlyInformationRepository hourlyInformationRepo;

    private HourlyWeatherService(HourlyInformationRepository hourlyInformationRep) {
        this.hourlyInformationRepo = hourlyInformationRep;
    }

    @Override
    public void callService(GeoLocationObject geoLocation) throws JsonSyntaxException, IOException, NullPointerException {
        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocation);

        ResponseBody responseBody = callRequestAndReturnResponseBody(urlBuilder);

        List<HourlyInformationEntity> listOfHourlyEntities = parseResponseBody(responseBody);
        addHourlyInformationToRepository(listOfHourlyEntities);
    }

    @Override
    protected List<HourlyInformationEntity> parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type hourlyType = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        List<JsonObject> unParsedHourlyJsonObjects = gson.fromJson(responseBody.string(), hourlyType);
        return unParsedHourlyJsonObjects.stream()
                .map(hour -> createHourlyInformationEntity(hour))
                .collect(Collectors.toList());
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        Date futureEndTime = DateUtils.addHours(new Date(), 5);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new HttpUrl.Builder().scheme(APIConstants.SCHEME).host(APIConstants.CLIMACELL_API_URL)
                .addPathSegment(APIConstants.VERSION)
                .addPathSegment("weather").addPathSegment("forecast").addPathSegment("hourly")
                .addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(APIConstants.UNIT_SYSTEM, APIConstants.SI)
                .addQueryParameter("start_time", "now")
                .addQueryParameter("end_time", formatter.format(futureEndTime))
                .addQueryParameter("fields", APIConstants.HOURLY_FIELDS)
                .addQueryParameter("apikey", APIConstants.CLIMACELL_API_KEY);
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
        hourInformatioObject.add(ServicesConstants.LAT, hourJsonObject.get(ServicesConstants.LAT));
        hourInformatioObject.add(ServicesConstants.LON, hourJsonObject.get(ServicesConstants.LON));
        hourInformatioObject.add(ServicesConstants.TEMPERATURE, getValueFromElement(hourJsonObject.get(ServicesConstants.TEMPERATURE)));
        hourInformatioObject.add(ServicesConstants.WEATHER_CODE, getValueFromElement(hourJsonObject.get(ServicesConstants.WEATHER_CODE)));
        hourInformatioObject.add(ServicesConstants.TIME, getValueFromElement(hourJsonObject.get(ServicesConstants.TIME)));
        hourInformatioObject.add(ServicesConstants.SUNSET, getValueFromElement(hourJsonObject.get(ServicesConstants.SUNSET)));
        hourInformatioObject.add(ServicesConstants.SUNRISE, getValueFromElement(hourJsonObject.get(ServicesConstants.SUNRISE)));

        return hourGson.fromJson(hourInformatioObject.toString(), HourlyInformationEntity.class);
    }
}