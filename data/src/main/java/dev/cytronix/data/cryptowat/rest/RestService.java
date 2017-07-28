package dev.cytronix.data.cryptowat.rest;

import dev.cytronix.data.cryptowat.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestService {

    @GET("markets/gdax/{targetCurrency}{baseCurrency}/price")
    Call<Result> getPrice(@Path("baseCurrency") String baseCurrency, @Path("targetCurrency") String targetCurrency);
}
