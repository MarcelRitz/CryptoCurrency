package dev.cytronix.data.cryptowat.repository;

import android.util.Log;

import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.model.Result;
import dev.cytronix.data.cryptowat.rest.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceRepository implements IPriceRepository {

    private String baseCurrency;
    private RestService service;
    private OnPriceRepositoryListener onPriceRepositoryListener;

    public PriceRepository(RestService service, String baseCurrency) {
        this.service = service;
        this.baseCurrency = baseCurrency;
    }

    @Override
    public void getPrice(final String toCurrency) {
        Call<Result> call = service.getPrice(baseCurrency, toCurrency);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                Result result = response.body();
                Price price = result.getPrice();
                if(price == null) {
                    onFailure(call, new Throwable("Unknown error"));
                    return;
                }

                price.setBaseCurrency(baseCurrency);
                price.setTargetCurrency(toCurrency);

                onPriceRepositoryListener.onSuccess(price);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable throwable) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                onPriceRepositoryListener.onError(throwable.getMessage());
            }
        });
    }

    @Override
    public void setOnPriceRepositoryListener(OnPriceRepositoryListener onPriceRepositoryListener) {
        this.onPriceRepositoryListener = onPriceRepositoryListener;
    }

    public interface OnPriceRepositoryListener {
        void onSuccess(Price price);

        void onError(String message);
    }
}
