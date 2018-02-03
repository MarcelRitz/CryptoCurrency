package dev.cytronix.cryptocurrency.storage;

import java.util.List;

import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.model.Price;

public interface IStorage {

    DataProvider getDataProvider();

    String getCurrency();

    Long getComplicationInverval();

    Long getComplicationInvervalLastTimestamp(int complicationId);

    void setComplicationInvervalLastTimestamp(int complicationId, long timestamp);

    void removeComplicationInvervalLastTimestamp(int... complicationIds);

    boolean getComplicationIntervalLocked(int complicationId);

    void setComplicationIntervalLocked(int complicationId, boolean bool);

    void removeComplicationIntervalLocked(int complicationId);

    boolean showPriceListSortInfo();

    @SuppressWarnings("SameParameterValue")
    void setPriceListSortInfo(boolean showed);

    void updatePriceListSort(List<Price> prices);

    void setPriceListSort(List<Price> prices);

    void updatePriceListQuantity(List<Price> prices);

    void updatePriceQuantity(Price price);
}
