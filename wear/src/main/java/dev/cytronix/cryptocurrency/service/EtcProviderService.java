package dev.cytronix.cryptocurrency.service;

import dev.cytronix.data.Currency;

public class EtcProviderService extends DataProviderService {

    public EtcProviderService() {
        super(Currency.ETC, ComplicationType.PRICE);
    }
}