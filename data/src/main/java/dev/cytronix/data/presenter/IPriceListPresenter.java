package dev.cytronix.data.presenter;

import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;

public interface IPriceListPresenter {

    void setBaseCurrency(String baseCurrency);

    List<Price> getPrices();

    void getData();
}
