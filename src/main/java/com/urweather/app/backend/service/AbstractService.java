package com.urweather.app.backend.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import org.springframework.scheduling.annotation.Async;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.HttpUrl.Builder;

import static com.urweather.app.helpers.ServicesConstants.VALUE;

public abstract class AbstractService<T, E> {

    protected final static OkHttpClient client = new OkHttpClient();

    abstract protected void storeEntityInChosenLocation(E entity);

    abstract protected E parseResponseBody(ResponseBody responseBody) throws JsonSyntaxException, IOException;

    abstract protected HttpUrl.Builder createUrlBuilder(T object);

    @Async
    public CompletableFuture<Boolean> callService(T type) {
        Builder urlBuilder = createUrlBuilder(type);
        E parsedEntity;
        try {
            ResponseBody responseBody = callRequestAndReturnResponseBody(urlBuilder);
            parsedEntity = parseResponseBody(responseBody);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(false);
        }

        storeEntityInChosenLocation(parsedEntity);
        return CompletableFuture.completedFuture(true);
    }

    protected JsonElement getValueFromElement(JsonElement element) {
        return element.getAsJsonObject().get(VALUE);
    }

    protected ResponseBody callRequestAndReturnResponseBody(HttpUrl.Builder urlBuilder) throws IOException {
        Request request = new Request.Builder().url(urlBuilder.toString()).get().build();

        Response response = client.newCall(request).execute();
        return response.body();
    }
}