package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class BchProviderService extends DataProviderService {

    public BchProviderService() {
        super(Currency.BCH);
    }
}