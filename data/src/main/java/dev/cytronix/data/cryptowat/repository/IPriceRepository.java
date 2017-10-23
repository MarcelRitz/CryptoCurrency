package dev.cytronix.data.cryptowat.repository;

public interface IPriceRepository extends IPriceBaseRepository {

    void getPrice(String targetCurrency);
}
