package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.data.Currency;

import static dev.cytronix.data.cryptowat.model.Price.PRICE_DEFAULT;

public class Prices {

    @SerializedName("bitstamp:bcheur")
    private double priceBitstampBchEur = PRICE_DEFAULT;

    @SerializedName("bitstamp:bchusd")
    private double priceBitstampBchUsd = PRICE_DEFAULT;

    @SerializedName("bitstamp:btceur")
    private double priceBitstampBtcEur = PRICE_DEFAULT;

    @SerializedName("bitstamp:btcusd")
    private double priceBitstampBtcUsd = PRICE_DEFAULT;

    @SerializedName("bitstamp:etheur")
    private double priceBitstampEthEur = PRICE_DEFAULT;

    @SerializedName("bitstamp:ethusd")
    private double priceBitstampEthUsd = PRICE_DEFAULT;

    @SerializedName("bitstamp:etceur")
    private double priceBitstampEtcEur = PRICE_DEFAULT;

    @SerializedName("bitstamp:etcusd")
    private double priceBitstampEtcUsd = PRICE_DEFAULT;

    @SerializedName("bitstamp:ltceur")
    private double priceBitstampLtcEur = PRICE_DEFAULT;

    @SerializedName("bitstamp:ltcusd")
    private double priceBitstampLtcUsd = PRICE_DEFAULT;

    @SerializedName("kraken:bcheur")
    private double priceKrakenBchEur = PRICE_DEFAULT;

    @SerializedName("kraken:bchusd")
    private double priceKrakenBchUsd = PRICE_DEFAULT;

    @SerializedName("kraken:btceur")
    private double priceKrakenBtcEur = PRICE_DEFAULT;

    @SerializedName("kraken:btcusd")
    private double priceKrakenBtcUsd = PRICE_DEFAULT;

    @SerializedName("kraken:etheur")
    private double priceKrakenEthEur = PRICE_DEFAULT;

    @SerializedName("kraken:ethusd")
    private double priceKrakenEthUsd = PRICE_DEFAULT;

    @SerializedName("kraken:etceur")
    private double priceKrakenEtcEur = PRICE_DEFAULT;

    @SerializedName("kraken:etcusd")
    private double priceKrakenEtcUsd = PRICE_DEFAULT;

    @SerializedName("kraken:ltceur")
    private double priceKrakenLtcEur = PRICE_DEFAULT;

    @SerializedName("kraken:ltcusd")
    private double priceKrakenLtcUsd = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:bcheur")
    private double priceCoinbaseBchEur = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:bchusd")
    private double priceCoinbaseBchUsd = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:btceur")
    private double priceCoinbaseBtcEur = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:btcusd")
    private double priceCoinbaseBtcUsd = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:etheur")
    private double priceCoinbaseEthEur = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:ethusd")
    private double priceCoinbaseEthUsd = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:etceur")
    private double priceCoinbaseEtcEur = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:etcusd")
    private double priceCoinbaseEtcUsd = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:ltceur")
    private double priceCoinbaseLtcEur = PRICE_DEFAULT;

    @SerializedName("coinbase-pro:ltcusd")
    private double priceCoinbaseLtcUsd = PRICE_DEFAULT;

    public List<Price> getList(DataProvider dataProvider, String baseCurrency) {
        switch (dataProvider) {
            case KRAKEN:
                return getListKraken(baseCurrency);
            case COINBASE:
                return getListCoinbase(baseCurrency);
            case BITSTAMP:
            default:
                return getListBitstamp(baseCurrency);
        }
    }

    private List<Price> getListBitstamp(String baseCurrency) {
        List<Price> prices = new ArrayList<>();
        switch (baseCurrency) {
            case Currency.EUR:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceBitstampBchEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceBitstampBtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceBitstampEthEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceBitstampEtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceBitstampLtcEur).build());
                break;
            case Currency.USD:
            default:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceBitstampBchUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceBitstampBtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceBitstampEthUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceBitstampEtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceBitstampLtcUsd).build());
                break;
        }
        return prices;
    }

    private List<Price> getListKraken(String baseCurrency) {
        List<Price> prices = new ArrayList<>();
        switch (baseCurrency) {
            case Currency.EUR:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceKrakenBchEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceKrakenBtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceKrakenEthEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceKrakenEtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceKrakenLtcEur).build());
                break;
            case Currency.USD:
            default:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceKrakenBchUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceKrakenBtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceKrakenEthUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceKrakenEtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceKrakenLtcUsd).build());
                break;
        }
        return prices;
    }

    private List<Price> getListCoinbase(String baseCurrency) {
        List<Price> prices = new ArrayList<>();
        switch (baseCurrency) {
            case Currency.EUR:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceCoinbaseBchEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceCoinbaseBtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceCoinbaseEthEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceCoinbaseEtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceCoinbaseLtcEur).build());
                break;
            case Currency.USD:
            default:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceCoinbaseBchUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceCoinbaseBtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceCoinbaseEthUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceCoinbaseEtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceCoinbaseLtcUsd).build());
                break;
        }
        return prices;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "priceBitstampBchEur=" + priceBitstampBchEur +
                ", priceBitstampBchUsd=" + priceBitstampBchUsd +
                ", priceBitstampBtcEur=" + priceBitstampBtcEur +
                ", priceBitstampBtcUsd=" + priceBitstampBtcUsd +
                ", priceBitstampEthEur=" + priceBitstampEthEur +
                ", priceBitstampEthUsd=" + priceBitstampEthUsd +
                ", priceBitstampEtcEur=" + priceBitstampEtcEur +
                ", priceBitstampEtcUsd=" + priceBitstampEtcUsd +
                ", priceBitstampLtcEur=" + priceBitstampLtcEur +
                ", priceBitstampLtcUsd=" + priceBitstampLtcUsd +
                ", priceKrakenBchEur=" + priceKrakenBchEur +
                ", priceKrakenBchUsd=" + priceKrakenBchUsd +
                ", priceKrakenBtcEur=" + priceKrakenBtcEur +
                ", priceKrakenBtcUsd=" + priceKrakenBtcUsd +
                ", priceKrakenEthEur=" + priceKrakenEthEur +
                ", priceKrakenEthUsd=" + priceKrakenEthUsd +
                ", priceKrakenEtcEur=" + priceKrakenEtcEur +
                ", priceKrakenEtcUsd=" + priceKrakenEtcUsd +
                ", priceKrakenLtcEur=" + priceKrakenLtcEur +
                ", priceKrakenLtcUsd=" + priceKrakenLtcUsd +
                ", priceCoinbaseBchEur=" + priceCoinbaseBchEur +
                ", priceCoinbaseBchUsd=" + priceCoinbaseBchUsd +
                ", priceCoinbaseBtcEur=" + priceCoinbaseBtcEur +
                ", priceCoinbaseBtcUsd=" + priceCoinbaseBtcUsd +
                ", priceCoinbaseEthEur=" + priceCoinbaseEthEur +
                ", priceCoinbaseEthUsd=" + priceCoinbaseEthUsd +
                ", priceCoinbaseEtcEur=" + priceCoinbaseEtcEur +
                ", priceCoinbaseEtcUsd=" + priceCoinbaseEtcUsd +
                ", priceCoinbaseLtcEur=" + priceCoinbaseLtcEur +
                ", priceCoinbaseLtcUsd=" + priceCoinbaseLtcUsd +
                '}';
    }
}
