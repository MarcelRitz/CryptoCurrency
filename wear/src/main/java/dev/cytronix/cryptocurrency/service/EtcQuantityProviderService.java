package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class EtcQuantityProviderService extends DataProviderService {

    public EtcQuantityProviderService() {
        super(Currency.ETC, ComplicationType.WALLET);
    }
}