package dev.cytronix.data.view;

import dev.cytronix.data.cryptowat.model.Price;

public interface PriceView {

    void onUpdate(Price price);

    void onError(String message);
}
