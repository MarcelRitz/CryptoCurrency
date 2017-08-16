package dev.cytronix.data.view;

import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;

public interface PriceListView {

    void onUpdate(List<Price> prices);

    void onError(String message);
}
