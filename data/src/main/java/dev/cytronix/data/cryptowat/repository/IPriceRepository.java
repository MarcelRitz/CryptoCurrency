package dev.cytronix.data.cryptowat.repository;

public interface IPriceRepository {

    void getPrice(String toCurrency);

    void setOnPriceRepositoryListener(PriceRepository.OnPriceRepositoryListener onPriceRepositoryListener);
}
