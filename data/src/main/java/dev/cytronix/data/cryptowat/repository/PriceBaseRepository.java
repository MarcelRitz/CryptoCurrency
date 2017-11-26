package dev.cytronix.data.cryptowat.repository;

import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.rest.RestService;

public class PriceBaseRepository implements IPriceBaseRepository {

    protected RestService service;
    protected DataProvider dataProvider;
    protected String baseCurrency;
    protected OnPriceRepositoryListener onPriceRepositoryListener;

    public PriceBaseRepository(RestService service, DataProvider dataProvider, String baseCurrency) {
        this.service = service;
        this.dataProvider = dataProvider;
        this.baseCurrency = baseCurrency;
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Override
    public void setOnPriceRepositoryListener(OnPriceRepositoryListener onPriceRepositoryListener) {
        this.onPriceRepositoryListener = onPriceRepositoryListener;
    }
}
