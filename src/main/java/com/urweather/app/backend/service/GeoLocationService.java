package com.urweather.app.backend.service;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.googlecode.gentyref.TypeToken;
import com.urweather.app.backend.entity.GeoLocationEntity;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.HttpUrl.Builder;

import static com.urweather.app.helpers.APIConstants.GEOLOCATION_API_KEY;
import static com.urweather.app.helpers.APIConstants.GEOLOCATION_API_URL;
import static com.urweather.app.helpers.APIConstants.GEOLOCATION_SCHEME;
import static com.urweather.app.helpers.APIConstants.GEOLOCATION_VERSION;

@Service
public class GeoLocationService extends AbstractService<String[], GeoLocationEntity>{

    private static GeoLocationEntity currentGeoLocation;

    @Override
    protected GeoLocationEntity parseResponseBody(ResponseBody responseBody)
                throws JsonSyntaxException, IOException, IndexOutOfBoundsException {
        Gson gson = new Gson();
        Type jsonType = new TypeToken<JsonObject>() {}.getType();
        JsonObject jsonObj = gson.fromJson(responseBody.string(), jsonType);

        Type geoLocationType = new TypeToken<GeoLocationEntity>() {}.getType();
        try {
            String jsonGeoData = jsonObj.get("data").getAsJsonArray().get(0).getAsJsonObject().toString();
            return gson.fromJson(jsonGeoData, geoLocationType);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("No data found relating to that City/Country. Please review your input.");
        }
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(String[] splitUserInput) {
        return new HttpUrl.Builder().scheme(GEOLOCATION_SCHEME).host(GEOLOCATION_API_URL)
                .addPathSegment(GEOLOCATION_VERSION).addPathSegment("geo").addPathSegment("cities")
                .addQueryParameter("namePrefix", splitUserInput[0])
                .addQueryParameter("countryIds", splitUserInput[1].replaceAll("\\s+", ""));
    }

    @Override
    protected ResponseBody callRequestAndReturnResponseBody(Builder urlBuilder) throws IOException {
        Request request = new Request.Builder().url(urlBuilder.toString())
                            .get()
                            .addHeader("x-rapidapi-host", GEOLOCATION_API_URL)
                            .addHeader("x-rapidapi-key", GEOLOCATION_API_KEY)
                            .build();

        Response response = client.newCall(request).execute();
        return response.body();
    }

    @Override
    protected void storeEntityInChosenLocation(GeoLocationEntity entity) {
        currentGeoLocation = entity;
    }

    public GeoLocationEntity getCurrentGeoLocation() {
        return currentGeoLocation;
    }
}