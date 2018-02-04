package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class BtcProviderService extends DataProviderService {

    public BtcProviderService() {
        super(Currency.BTC, ComplicationType.PRICE);
    }
}