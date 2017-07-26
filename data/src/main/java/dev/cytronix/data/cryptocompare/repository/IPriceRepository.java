package dev.cytronix.data.cryptocompare.repository;

public interface IPriceRepository {

    void getPrice(String toCurrency);

    void setOnPriceRepositoryListener(PriceRepository.OnPriceRepositoryListener onPriceRepositoryListener);
}
