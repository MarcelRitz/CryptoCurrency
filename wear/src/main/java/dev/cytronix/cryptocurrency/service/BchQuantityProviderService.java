package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class BchQuantityProviderService extends DataProviderService {

    public BchQuantityProviderService() {
        super(Currency.BCH, true);
    }
}