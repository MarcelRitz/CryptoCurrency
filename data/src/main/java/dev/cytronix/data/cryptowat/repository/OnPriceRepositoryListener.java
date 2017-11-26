package dev.cytronix.data.cryptowat.repository;

import java.util.List;

import dev.cytronix.data.cryptowat.model.Price;

public interface OnPriceRepositoryListener {
    void onSuccess(List<Price> prices);

    void onError(String message);
}