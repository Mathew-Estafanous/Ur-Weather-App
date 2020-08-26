package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.backend.entity.NowcastObject;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class NowcastWeatherService {

    private final String API_KEY = "jZdP0f1KuUvdEIrQPLomXIQGdutw9mI1";
    private final String API_URL = "api.climacell.co";

    private static NowcastObject currentNowcastInformation;

    public void createNowcastObjectFromGeoLocation(GeoLocationObject geoLocationObject) throws JsonSyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = createNowcastUrlBuilder(geoLocationObject);

        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        currentNowcastInformation = parseResponseBody(responseBody);
    }

    public NowcastObject getCurreNowcastObject() {
        return currentNowcastInformation;
    }

    private NowcastObject parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        JsonObject responseJsonObject = gson.fromJson(responseBody.string(), JsonObject.class);
        JsonObject nowcastJsonObject = new JsonObject();
        nowcastJsonObject.add("lat", responseJsonObject.get("lat"));
        nowcastJsonObject.add("lon", responseJsonObject.get("lon"));
        nowcastJsonObject.add("temp", responseJsonObject.get("temp").getAsJsonObject().get("value"));
        nowcastJsonObject.add("weather_code", responseJsonObject.get("weather_code").getAsJsonObject().get("value"));
        nowcastJsonObject.add("observation_time", responseJsonObject.get("observation_time").getAsJsonObject().get("value"));
        return gson.fromJson(nowcastJsonObject.toString(), NowcastObject.class);
    }

    private HttpUrl.Builder createNowcastUrlBuilder(GeoLocationObject geoLocation) {
        return new HttpUrl.Builder().scheme("https").host(API_URL).addPathSegment("v3")
                .addPathSegment("weather").addPathSegment("realtime")
                .addQueryParameter("lat", Double.toString(geoLocation.getLatitude()))
                .addQueryParameter("lon", Double.toString(geoLocation.getLongitude()))
                .addQueryParameter("unit_system", "si")
                .addQueryParameter("fields", "temp,weather_code")
                .addQueryParameter("apikey", API_KEY);
    }
}