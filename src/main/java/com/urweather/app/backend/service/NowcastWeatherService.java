package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.entity.NowcastObject;
import com.urweather.app.helpers.APIConstants;
import com.urweather.app.helpers.ServicesConstants;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

@Service
public class NowcastWeatherService extends AbstractService<GeoLocationObject, NowcastObject, GeoLocationObject> {

    private static NowcastObject currentNowcastInformation;

    @Override
    public void callService(GeoLocationObject geoLocationObject) throws JsonSyntaxException, IOException {
        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocationObject);

        ResponseBody responseBody = callRequestAndReturnResponseBody(urlBuilder);

        currentNowcastInformation = parseResponseBody(responseBody);
    }

    @Override
    protected NowcastObject parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        JsonObject responseJsonObject = gson.fromJson(responseBody.string(), JsonObject.class);
        JsonObject nowcastJsonObject = new JsonObject();
        nowcastJsonObject.add(ServicesConstants.LAT, responseJsonObject.get(ServicesConstants.LAT));
        nowcastJsonObject.add(ServicesConstants.LON, responseJsonObject.get(ServicesConstants.LON));
        nowcastJsonObject.add(ServicesConstants.TEMPERATURE, getValueFromElement(responseJsonObject.get(ServicesConstants.TEMPERATURE)));
        nowcastJsonObject.add(ServicesConstants.WEATHER_CODE, getValueFromElement(responseJsonObject.get(ServicesConstants.WEATHER_CODE)));
        nowcastJsonObject.add(ServicesConstants.TIME, getValueFromElement(responseJsonObject.get(ServicesConstants.TIME )));
        nowcastJsonObject.add(ServicesConstants.SUNRISE, getValueFromElement(responseJsonObject.get(ServicesConstants.SUNRISE)));
        nowcastJsonObject.add(ServicesConstants.SUNSET, getValueFromElement(responseJsonObject.get(ServicesConstants.SUNSET)));
        return gson.fromJson(nowcastJsonObject.toString(), NowcastObject.class);
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        return new HttpUrl.Builder().scheme(APIConstants.SCHEME).host(APIConstants.CLIMACELL_API_URL)
                .addPathSegment(APIConstants.VERSION).addPathSegment("weather")
                .addPathSegment("realtime")
                .addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(APIConstants.UNIT_SYSTEM, APIConstants.SI)
                .addQueryParameter("fields", APIConstants.NOWCAST_FIELDS)
                .addQueryParameter("apikey", APIConstants.CLIMACELL_API_KEY);
    }

    public NowcastObject getCurreNowcastObject() {
        return currentNowcastInformation;
    }
}