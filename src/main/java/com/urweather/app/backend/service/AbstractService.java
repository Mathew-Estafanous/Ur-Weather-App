package com.urweather.app.backend.service;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

public abstract class AbstractService<T, E, G> {

    abstract public void callService(T type) throws JsonSyntaxException, IOException, NullPointerException;

    abstract E parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException;

    abstract HttpUrl.Builder createUrlBuilder(G object);
}