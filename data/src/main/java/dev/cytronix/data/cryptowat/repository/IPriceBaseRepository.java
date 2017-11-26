package dev.cytronix.data.cryptowat.repository;

public interface IPriceBaseRepository {

    void setBaseCurrency(String baseCurrency);

    void setOnPriceRepositoryListener(OnPriceRepositoryListener onPriceRepositoryListener);
}
