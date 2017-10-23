package dev.cytronix.data.cryptowat.repository;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.model.ResultPrice;
import dev.cytronix.data.cryptowat.rest.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceRepository extends PriceBaseRepository implements IPriceRepository {

    public PriceRepository(RestService service, String baseCurrency) {
        super(service, baseCurrency);
    }

    @Override
    public void getPrice(final String targetCurrency) {
        Call<ResultPrice> call = service.getPrice(baseCurrency, targetCurrency);
        call.enqueue(new Callback<ResultPrice>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<ResultPrice> call, Response<ResultPrice> response) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                ResultPrice resultPrice = response.body();
                if(resultPrice == null) {
                    onFailure(call, new Throwable("Unknown error"));
                    return;
                }

                Price price = resultPrice.getPrice();
                price.setBaseCurrency(baseCurrency);
                price.setTargetCurrency(targetCurrency);

                List<Price> prices = new ArrayList<>(1);
                prices.add(price);
                onPriceRepositoryListener.onSuccess(prices);
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<ResultPrice> call, Throwable throwable) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                onPriceRepositoryListener.onError(throwable.getMessage());
            }
        });
    }
}
