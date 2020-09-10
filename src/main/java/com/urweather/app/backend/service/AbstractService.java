package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

import static com.urweather.app.helpers.ServicesConstants.VALUE;

public abstract class AbstractService<T, E, G> {

    abstract public void callService(T type) throws JsonSyntaxException, IOException, NullPointerException;

    abstract protected E parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException;

    abstract protected HttpUrl.Builder createUrlBuilder(G object);

    protected JsonElement getValueFromElement(JsonElement element) {
        return element.getAsJsonObject().get(VALUE);
    }
}