package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.urweather.app.backend.entity.DetailWeatherObject;
import com.urweather.app.backend.entity.GeoLocationObject;
import com.urweather.app.helpers.APIConstants;
import com.urweather.app.helpers.ServicesConstants;

import org.springframework.stereotype.Service;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

@Service
public class DetailWeatherService extends AbstractService<GeoLocationObject, DetailWeatherObject, GeoLocationObject> {

    private static DetailWeatherObject currentDetailWeatherInformation;

    @Override
    public void callService(GeoLocationObject geoLocation) throws JsonSyntaxException, IOException, NullPointerException {
        HttpUrl.Builder urlBuilder = createUrlBuilder(geoLocation);

        ResponseBody responseBody = callRequestAndReturnResponseBody(urlBuilder);

        currentDetailWeatherInformation = parseResponseBody(responseBody);
    }

    @Override
    protected DetailWeatherObject parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        JsonObject responseJsonObject = gson.fromJson(responseBody.string(), JsonObject.class);
        JsonObject detailInfoObject = new JsonObject();
        detailInfoObject.add(ServicesConstants.LAT, responseJsonObject.get(ServicesConstants.LAT));
        detailInfoObject.add(ServicesConstants.LON, responseJsonObject.get(ServicesConstants.LON));
        detailInfoObject.add(ServicesConstants.SUNRISE, getValueFromElement(responseJsonObject.get(ServicesConstants.SUNRISE)));
        detailInfoObject.add(ServicesConstants.SUNSET, getValueFromElement(responseJsonObject.get(ServicesConstants.SUNSET)));
        detailInfoObject.add(ServicesConstants.CLOUD_COVER, getValueFromElement(responseJsonObject.get(ServicesConstants.CLOUD_COVER)));
        detailInfoObject.add(ServicesConstants.HUMIDITY, getValueFromElement(responseJsonObject.get(ServicesConstants.HUMIDITY)));
        detailInfoObject.add(ServicesConstants.PRESSURE, getValueFromElement(responseJsonObject.get(ServicesConstants.PRESSURE)));
        detailInfoObject.add(ServicesConstants.VISIBILITY, getValueFromElement(responseJsonObject.get(ServicesConstants.VISIBILITY)));
        return gson.fromJson(detailInfoObject.toString(), DetailWeatherObject.class);
    }

    @Override
    protected HttpUrl.Builder createUrlBuilder(GeoLocationObject geoLocation) {
        return new HttpUrl.Builder().scheme(APIConstants.SCHEME).host(APIConstants.CLIMACELL_API_URL)
                .addPathSegment(APIConstants.VERSION).addPathSegment("weather")
                .addPathSegment("realtime").addQueryParameter(ServicesConstants.LAT, Double.toString(geoLocation.getLatitude()))
                .addQueryParameter(ServicesConstants.LON, Double.toString(geoLocation.getLongitude()))
                .addQueryParameter(APIConstants.UNIT_SYSTEM, APIConstants.SI)
                .addQueryParameter("fields", APIConstants.DETAILS_FIELDS)
                .addQueryParameter("apikey", APIConstants.CLIMACELL_API_KEY);
    }

    public DetailWeatherObject getDetailWeatherInformation() {
        return currentDetailWeatherInformation;
    }
}