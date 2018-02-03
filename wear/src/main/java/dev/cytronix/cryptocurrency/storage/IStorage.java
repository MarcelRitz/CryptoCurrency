package dev.cytronix.cryptocurrency.storage;

import java.util.List;

import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.model.Price;

public interface IStorage {

    DataProvider getDataProvider();

    String getCurrency();

    Long getComplicationInverval();

    boolean showPriceListSortInfo();

    @SuppressWarnings("SameParameterValue")
    void setPriceListSortInfo(boolean showed);

    void updatePriceListSort(List<Price> prices);

    void setPriceListSort(List<Price> prices);

    void updatePriceListQuantity(List<Price> prices);

    void updatePriceQuantity(Price price);
}
