package dev.cytronix.data.cryptocompare.repository;

import android.util.Log;

import dev.cytronix.data.cryptocompare.model.ChfPrice;
import dev.cytronix.data.cryptocompare.model.Currency;
import dev.cytronix.data.cryptocompare.model.Price;
import dev.cytronix.data.cryptocompare.model.UsdPrice;
import dev.cytronix.data.cryptocompare.rest.RestClient;
import dev.cytronix.data.cryptocompare.rest.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceRepository implements IPriceRepository {

    private String baseCurrency;
    private RestService service;
    private OnPriceRepositoryListener onPriceRepositoryListener;

    public PriceRepository(String baseCurrency) {
        this.baseCurrency = baseCurrency;

        service = new RestClient().getService();
    }

    @Override
    public void getPrice(String toCurrency) {
        switch(toCurrency) {
            case Currency.CHF:
                getChfPrice(toCurrency);
                break;
            case Currency.USD:
            default:
                getUsdPrice(toCurrency);
                break;
        }
    }

    private void getChfPrice(String toCurrency) {
        Call<ChfPrice> call = service.getChfPrice(baseCurrency, toCurrency);
        call.enqueue(new Callback<ChfPrice>() {
            @Override
            public void onResponse(Call<ChfPrice> call, Response<ChfPrice> response) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                onPriceRepositoryListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ChfPrice> call, Throwable throwable) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                onPriceRepositoryListener.onError(throwable.getMessage());
            }
        });
    }

    private void getUsdPrice(String toCurrency) {
        Call<UsdPrice> call = service.getUsdPrice(baseCurrency, toCurrency.toUpperCase());
        call.enqueue(new Callback<UsdPrice>() {
            @Override
            public void onResponse(Call<UsdPrice> call, Response<UsdPrice> response) {
                if(onPriceRepositoryListener == null) {
                    return;
                }

                onPriceRepositoryListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UsdPrice> call, Throwable throwable) {
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
