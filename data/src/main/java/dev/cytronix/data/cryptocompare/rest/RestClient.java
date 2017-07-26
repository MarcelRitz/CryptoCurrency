package dev.cytronix.data.cryptocompare.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private RestService service;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://min-api.cryptocompare.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RestService.class);
    }

    public RestService getService() {
        return service;
    }
}
