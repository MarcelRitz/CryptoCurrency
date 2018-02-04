package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class BtcQuantityProviderService extends DataProviderService {

    public BtcQuantityProviderService() {
        super(Currency.BTC, ComplicationType.WALLET);
    }
}