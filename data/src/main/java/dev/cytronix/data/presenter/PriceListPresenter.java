package dev.cytronix.data.presenter;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.data.Currency;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.repository.IPriceRepository;
import dev.cytronix.data.cryptowat.repository.PriceRepository;
import dev.cytronix.data.cryptowat.rest.RestClient;
import dev.cytronix.data.view.PriceListView;

public class PriceListPresenter implements IPriceListPresenter, PriceRepository.OnPriceRepositoryListener {

    private List<Price> prices = new ArrayList<>();
    private IPriceRepository repository;
    private PriceListView view;
    private String baseCurrency;

    public PriceListPresenter(PriceListView view, String baseCurrency) {
        this.view = view;
        this.baseCurrency = baseCurrency;

        setPriceList();
        setRepository();
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;

        setPriceList();
        setRepository();
    }

    @Override
    public List<Price> getPrices() {
        return prices;
    }

    private void setPriceList() {
        prices.clear();
        prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).build());
        prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).build());
        prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).build());
        prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).build());
    }

    private void setRepository() {
        repository = new PriceRepository(new RestClient().getService(), baseCurrency);
        repository.setOnPriceRepositoryListener(this);
    }

    @Override
    public void getData() {
        for(Price price:prices) {
            repository.getPrice(price.getTargetCurrency());
        }
    }

    @Override
    public void onSuccess(Price price) {
        for(Price p:prices) {
            if(p.getTargetCurrency().equals(price.getTargetCurrency())) {
                p.setPrice(price.getPrice());
                break;
            }
        }

        view.onUpdate(prices);
    }

    @Override
    public void onError(String message) {
        view.onError(message);
    }
}
