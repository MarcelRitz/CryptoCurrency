package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class EthProviderService extends DataProviderService {

    public EthProviderService() {
        super(Currency.ETH);
    }
}