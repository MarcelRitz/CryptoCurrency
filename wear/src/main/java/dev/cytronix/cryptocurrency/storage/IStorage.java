package dev.cytronix.cryptocurrency.storage;

import dev.cytronix.data.cryptowat.model.DataProvider;

public interface IStorage {

    DataProvider getDataProvider();

    String getCurrency();
}
