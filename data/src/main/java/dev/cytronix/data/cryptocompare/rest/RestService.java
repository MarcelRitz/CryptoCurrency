package dev.cytronix.data.cryptocompare.rest;

import dev.cytronix.data.cryptocompare.model.ChfPrice;
import dev.cytronix.data.cryptocompare.model.UsdPrice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestService {

    @GET("data/price")
    Call<ChfPrice> getChfPrice(@Query("fsym") String fromSymbol, @Query("tsyms") String toSymbols);

    @GET("data/price")
    Call<UsdPrice> getUsdPrice(@Query("fsym") String fromSymbol, @Query("tsyms") String toSymbols);
}
