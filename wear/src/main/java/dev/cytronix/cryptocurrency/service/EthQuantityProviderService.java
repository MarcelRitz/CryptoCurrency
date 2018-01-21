package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class EthQuantityProviderService extends DataProviderService {

    public EthQuantityProviderService() {
        super(Currency.ETH, true);
    }
}