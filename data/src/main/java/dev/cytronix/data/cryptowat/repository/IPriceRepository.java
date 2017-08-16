package dev.cytronix.data.cryptowat.repository;

public interface IPriceRepository {

    void setBaseCurrency(String baseCurrency);

    void getPrice(String targetCurrency);

    void setOnPriceRepositoryListener(PriceRepository.OnPriceRepositoryListener onPriceRepositoryListener);
}
