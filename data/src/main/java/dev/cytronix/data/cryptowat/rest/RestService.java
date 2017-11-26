package dev.cytronix.data.cryptowat.rest;

import dev.cytronix.data.cryptowat.model.ResultPrice;
import dev.cytronix.data.cryptowat.model.ResultPrices;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestService {

    @GET("markets/{dataProvider}/{targetCurrency}{baseCurrency}/price")
    Call<ResultPrice> getPrice(@Path("dataProvider") String provider, @Path("baseCurrency") String baseCurrency, @Path("targetCurrency") String targetCurrency);

    @GET("markets/prices")
    Call<ResultPrices> getPrices();
}
