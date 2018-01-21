package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class LtcProviderService extends DataProviderService {

    public LtcProviderService() {
        super(Currency.LTC, false);
    }
}