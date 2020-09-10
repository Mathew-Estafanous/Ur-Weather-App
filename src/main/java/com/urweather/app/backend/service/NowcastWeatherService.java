package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.entity.NowcastObject;
import com.urweather.app.helpers.ServicesConstants;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.urweather.app.helpers.APIConstants.CLIMACELL_API_KEY;
import static com.urweather.app.helpers.APIConstants.CLIMACELL_API_URL;
import static com.urweather.app.helpers.APIConstants.UNIT_SYSTEM;
import static com.urweather.app.helpers.APIConstants.SI;
import static com.urweather.app.helpers.APIConstants.NOWCAST_FIELDS;;

@Service
public class NowcastWeatherService extends AbstractService<GeoLocationObject, NowcastObject, GeoLocationObject> {

    private static NowcastObject currentNowcastInformation;

    @Override
    public void callService(GeoLocationObject geoLocationObject) throws JsonSyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocationObject);

        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

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
        return new HttpUrl.Builder().scheme("https").host(CLIMACELL_API_URL)
                .addPathSegment("v3").addPathSegment("weather")
                .addPathSegment("realtime")
                .addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(UNIT_SYSTEM, SI)
                .addQueryParameter("fields", NOWCAST_FIELDS)
                .addQueryParameter("apikey", CLIMACELL_API_KEY);
    }

    public NowcastObject getCurreNowcastObject() {
        return currentNowcastInformation;
    }
}