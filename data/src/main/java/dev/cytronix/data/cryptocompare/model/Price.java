package dev.cytronix.data.cryptocompare.model;

public class Price {

    private String currency;

    public Price(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency='" + currency + '\'' +
                '}';
    }
}
