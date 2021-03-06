package dev.cytronix.data.presenter;

import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.repository.IPriceRepository;
import dev.cytronix.data.cryptowat.repository.OnPriceRepositoryListener;
import dev.cytronix.data.cryptowat.repository.PriceRepository;
import dev.cytronix.data.cryptowat.rest.RestClient;
import dev.cytronix.data.view.PriceView;

public class PricePresenter implements IPricePresenter, OnPriceRepositoryListener {

    private DataProvider dataProvider;
    private String baseCurrency;
    private PriceView view;
    private IPriceRepository repository;

    public PricePresenter(DataProvider dataProvider, String baseCurrency, PriceView view) {
        this.dataProvider = dataProvider;
        this.baseCurrency = baseCurrency;
        this.view = view;

        setRepository();
    }

    private void setRepository() {
        repository = new PriceRepository(new RestClient().getService(), dataProvider, baseCurrency);
        repository.setOnPriceRepositoryListener(this);
    }

    @Override
    public void getData(String toCurrency) {
        repository.getPrice(toCurrency);
    }

    @Override
    public void onSuccess(List<Price> prices) {
        view.onUpdate(prices);
    }

    @Override
    public void onError(String message) {
        view.onError(message);
    }
}
