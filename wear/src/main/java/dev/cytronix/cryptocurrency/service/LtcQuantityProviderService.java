package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class LtcQuantityProviderService extends DataProviderService {

    public LtcQuantityProviderService() {
        super(Currency.LTC, true);
    }
}