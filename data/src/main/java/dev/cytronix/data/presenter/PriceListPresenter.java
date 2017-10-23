package dev.cytronix.data.presenter;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.repository.IPricesRepository;
import dev.cytronix.data.cryptowat.repository.OnPriceRepositoryListener;
import dev.cytronix.data.cryptowat.repository.PricesRepository;
import dev.cytronix.data.cryptowat.rest.RestClient;
import dev.cytronix.data.view.PriceListView;

public class PriceListPresenter implements IPriceListPresenter, OnPriceRepositoryListener {

    private List<Price> prices = new ArrayList<>();
    private IPricesRepository repository;
    private PriceListView view;
    private String baseCurrency;

    public PriceListPresenter(PriceListView view, String baseCurrency) {
        this.view = view;
        this.baseCurrency = baseCurrency;

        setRepository();
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;

        setRepository();
    }

    @Override
    public List<Price> getPrices() {
        return prices;
    }

    private void setRepository() {
        repository = new PricesRepository(new RestClient().getService(), baseCurrency);
        repository.setOnPriceRepositoryListener(this);
    }

    @Override
    public void getData() {
        repository.getPrices();
    }

    @Override
    public void onSuccess(List<Price> prices) {
        this.prices.clear();
        this.prices.addAll(prices);

        view.onUpdate(this.prices);
    }

    @Override
    public void onError(String message) {
        view.onError(message);
    }
}
