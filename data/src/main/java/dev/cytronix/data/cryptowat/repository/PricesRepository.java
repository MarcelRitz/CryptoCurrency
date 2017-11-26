package dev.cytronix.data.cryptowat.repository;

import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.model.ResultPrices;
import dev.cytronix.data.cryptowat.rest.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PricesRepository extends PriceBaseRepository implements IPricesRepository {

    public PricesRepository(RestService service, DataProvider dataProvider, String baseCurrency) {
        super(service, dataProvider, baseCurrency);
    }

    @Override
    public void getPrices() {
        Call<ResultPrices> call = service.getPrices();
        call.enqueue(new Callback<ResultPrices>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<ResultPrices> call, Response<ResultPrices> response) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                ResultPrices resultPrices = response.body();
                if(resultPrices == null) {
                    onFailure(call, new Throwable("Unknown error"));
                    return;
                }

                onPriceRepositoryListener.onSuccess(resultPrices.getPrices().getList(dataProvider, baseCurrency));
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<ResultPrices> call, Throwable throwable) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                onPriceRepositoryListener.onError(throwable.getMessage());
            }
        });
    }
}
