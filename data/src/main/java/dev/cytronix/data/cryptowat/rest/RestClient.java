package dev.cytronix.data.cryptowat.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private RestService service;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cryptowat.ch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RestService.class);
    }

    public RestService getService() {
        return service;
    }
}
