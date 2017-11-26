package dev.cytronix.data.presenter;

import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.model.DataProvider;

public interface IPriceListPresenter {

    void setDataProvider(DataProvider dataProvider);

    void setBaseCurrency(String baseCurrency);

    List<Price> getPrices();

    void getData();
}
