package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.DetailWeatherObject;
import com.urweather.app.backend.entity.GeoLocationObject;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class DetailWeatherService extends AbstractService<GeoLocationObject, DetailWeatherObject, GeoLocationObject> {

    private final String API_KEY = "jZdP0f1KuUvdEIrQPLomXIQGdutw9mI1";
    private final String API_URL = "api.climacell.co";

    private static DetailWeatherObject currentDetailWeatherInformation;

    @Override
    public void callService(GeoLocationObject geoLocation) throws JsonSyntaxException, IOException, NullPointerException {
        if(geoLocation == null) {
            throw new NullPointerException("Geo location is null!");
        }
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocation);

        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        currentDetailWeatherInformation = parseResponseBody(responseBody);
    }

    @Override
    protected DetailWeatherObject parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        JsonObject responseJsonObject = gson.fromJson(responseBody.string(), JsonObject.class);
        JsonObject detailInfoObject = new JsonObject();
        detailInfoObject.add("lat", responseJsonObject.get("lat"));
        detailInfoObject.add("lon", responseJsonObject.get("lon"));
        detailInfoObject.add("sunrise", responseJsonObject.get("sunrise").getAsJsonObject().get("value"));
        detailInfoObject.add("sunset", responseJsonObject.get("sunset").getAsJsonObject().get("value"));
        detailInfoObject.add("cloud_cover", responseJsonObject.get("cloud_cover").getAsJsonObject().get("value"));
        detailInfoObject.add("humidity", responseJsonObject.get("humidity").getAsJsonObject().get("value"));
        detailInfoObject.add("baro_pressure", responseJsonObject.get("baro_pressure").getAsJsonObject().get("value"));
        detailInfoObject.add("visibility", responseJsonObject.get("visibility").getAsJsonObject().get("value"));
        return gson.fromJson(detailInfoObject.toString(), DetailWeatherObject.class);
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        return new HttpUrl.Builder().scheme("https").host(API_URL).addPathSegment("v3").addPathSegment("weather")
        .addPathSegment("realtime").addQueryParameter("lat", Double.toString(geoLocation.getLatitude()))
        .addQueryParameter("lon", Double.toString(geoLocation.getLongitude()))
        .addQueryParameter("unit_system", "si")
        .addQueryParameter("fields", "sunrise,sunset,humidity,visibility,cloud_cover,baro_pressure")
        .addQueryParameter("apikey", API_KEY);
    }

    public DetailWeatherObject getDetailWeatherInformation() {
        return currentDetailWeatherInformation;
    }
}