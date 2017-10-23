package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.data.Currency;

import static dev.cytronix.data.cryptowat.model.Price.PRICE_DEFAULT;

public class Prices {

    @SerializedName("kraken:bcheur")
    private double priceBchEur = PRICE_DEFAULT;

    @SerializedName("kraken:bchusd")
    private double priceBchUsd = PRICE_DEFAULT;

    @SerializedName("kraken:btceur")
    private double priceBtcEur = PRICE_DEFAULT;

    @SerializedName("kraken:btcusd")
    private double priceBtcUsd = PRICE_DEFAULT;

    @SerializedName("kraken:etheur")
    private double priceEthEur = PRICE_DEFAULT;

    @SerializedName("kraken:ethusd")
    private double priceEthUsd = PRICE_DEFAULT;

    @SerializedName("kraken:ltceur")
    private double priceLtcEur = PRICE_DEFAULT;

    @SerializedName("kraken:ltcusd")
    private double priceLtcUsd = PRICE_DEFAULT;

    public List<Price> getList(String currency) {
        List<Price> prices = new ArrayList<>(4);
        switch (currency) {
            case Currency.EUR:
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.BCH).addPrice(priceBchEur).build());
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.BTC).addPrice(priceBtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.ETH).addPrice(priceEthEur).build());
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.LTC).addPrice(priceLtcEur).build());
                break;
            case Currency.USD:
            default:
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.BCH).addPrice(priceBchUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.BTC).addPrice(priceBtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.ETH).addPrice(priceEthUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(currency).addTargetCurrency(Currency.LTC).addPrice(priceLtcUsd).build());
                break;
        }
        return prices;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "priceBchEur=" + priceBchEur +
                ", priceBchUsd=" + priceBchUsd +
                ", priceBtcEur=" + priceBtcEur +
                ", priceBtcUsd=" + priceBtcUsd +
                ", priceEthEur=" + priceEthEur +
                ", priceEthUsd=" + priceEthUsd +
                ", priceLtcEur=" + priceLtcEur +
                ", priceLtcUsd=" + priceLtcUsd +
                '}';
    }
}
