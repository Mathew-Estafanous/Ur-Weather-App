package com.urweather.app.backend.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.InputMismatchException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.googlecode.gentyref.TypeToken;
import com.urweather.app.backend.entity.GeoLocationEntity;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.HttpUrl.Builder;

import static com.urweather.app.helpers.APIConstants.GEOLOCATION_API_KEY;
import static com.urweather.app.helpers.APIConstants.GEOLOCATION_API_URL;
import static com.urweather.app.helpers.APIConstants.GEOLOCATION_SCHEME;
import static com.urweather.app.helpers.APIConstants.GEOLOCATION_VERSION;

@Service
public class GeoLocationService extends AbstractService<String, GeoLocationEntity, String[]>{

    private static GeoLocationEntity currentGeoLocation;

    @Override
    public final void callService(String userInput) throws JsonSyntaxException, IOException, NullPointerException {
        String[] splitUserInput = parseAndReturnCityAndCountry(userInput);

        HttpUrl.Builder urlBuilder = createUrlBuilder(splitUserInput);

        ResponseBody responseBody = callRequestAndReturnResponseBody(urlBuilder);

        currentGeoLocation = parseResponseBody(responseBody);
    }

    @Override
    protected GeoLocationEntity parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type jsonType = new TypeToken<JsonObject>() {}.getType();
        JsonObject jsonObj = gson.fromJson(responseBody.string(), jsonType);

        Type geoLocationType = new TypeToken<GeoLocationEntity>() {}.getType();
        try {
            return gson.fromJson(jsonObj.get("data").getAsJsonArray().get(0).getAsJsonObject().toString(), geoLocationType);
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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlBuilder.toString())
                            .get()
                            .addHeader("x-rapidapi-host", GEOLOCATION_API_URL)
                            .addHeader("x-rapidapi-key", GEOLOCATION_API_KEY)
                            .build();

        Response response = client.newCall(request).execute();
        return response.body();
    }

    public GeoLocationEntity getCurrentGeoLocation() {
        return currentGeoLocation;
    }

    private String[] parseAndReturnCityAndCountry(String userInput) throws InputMismatchException {
        String[] splitInput = userInput.split(",");
        if(splitInput.length != 2) {
            throw new InputMismatchException("Please make sure input is in similar format to, 'Richmond Hill, CA'");
        }
        return splitInput;
    }
}