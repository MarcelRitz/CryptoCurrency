package dev.cytronix.data.cryptowat.repository;

import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.model.Result;
import dev.cytronix.data.cryptowat.rest.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceRepository implements IPriceRepository {

    private RestService service;
    private String baseCurrency;
    private OnPriceRepositoryListener onPriceRepositoryListener;

    public PriceRepository(RestService service, String baseCurrency) {
        this.service = service;
        this.baseCurrency = baseCurrency;
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Override
    public void getPrice(final String targetCurrency) {
        Call<Result> call = service.getPrice(baseCurrency, targetCurrency);
        call.enqueue(new Callback<Result>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(onPriceRepositoryListener == null) {
                    return;
                }
                Result result = response.body();
                if(result == null) {
                    onFailure(call, new Throwable("Unknown error"));
                    return;
                }

                Price price = result.getPrice();
                price.setBaseCurrency(baseCurrency);
                price.setTargetCurrency(targetCurrency);

                onPriceRepositoryListener.onSuccess(price);
            }

            @SuppressWarnings("NullableProblems")
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
