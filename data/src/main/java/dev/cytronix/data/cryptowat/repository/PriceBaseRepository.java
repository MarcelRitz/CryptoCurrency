package dev.cytronix.data.cryptowat.repository;

import dev.cytronix.data.cryptowat.rest.RestService;

public class PriceBaseRepository implements IPriceBaseRepository {

    protected RestService service;
    protected String baseCurrency;
    protected OnPriceRepositoryListener onPriceRepositoryListener;

    public PriceBaseRepository(RestService service, String baseCurrency) {
        this.service = service;
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
