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

    @SerializedName("gdax:bcheur")
    private double priceGdaxBchEur = PRICE_DEFAULT;

    @SerializedName("gdax:bchusd")
    private double priceGdaxBchUsd = PRICE_DEFAULT;

    @SerializedName("gdax:btceur")
    private double priceGdaxBtcEur = PRICE_DEFAULT;

    @SerializedName("gdax:btcusd")
    private double priceGdaxBtcUsd = PRICE_DEFAULT;

    @SerializedName("gdax:etheur")
    private double priceGdaxEthEur = PRICE_DEFAULT;

    @SerializedName("gdax:ethusd")
    private double priceGdaxEthUsd = PRICE_DEFAULT;

    @SerializedName("gdax:etceur")
    private double priceGdaxEtcEur = PRICE_DEFAULT;

    @SerializedName("gdax:etcusd")
    private double priceGdaxEtcUsd = PRICE_DEFAULT;

    @SerializedName("gdax:ltceur")
    private double priceGdaxLtcEur = PRICE_DEFAULT;

    @SerializedName("gdax:ltcusd")
    private double priceGdaxLtcUsd = PRICE_DEFAULT;

    public List<Price> getList(DataProvider dataProvider, String baseCurrency) {
        switch (dataProvider) {
            case KRAKEN:
                return getListKraken(baseCurrency);
            case GDAX:
                return getListGdax(baseCurrency);
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

    private List<Price> getListGdax(String baseCurrency) {
        List<Price> prices = new ArrayList<>();
        switch (baseCurrency) {
            case Currency.EUR:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceGdaxBchEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceGdaxBtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceGdaxEthEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceGdaxEtcEur).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceGdaxLtcEur).build());
                break;
            case Currency.USD:
            default:
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BCH).addPrice(priceGdaxBchUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.BTC).addPrice(priceGdaxBtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETH).addPrice(priceGdaxEthUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.ETC).addPrice(priceGdaxEtcUsd).build());
                prices.add(new Price.Builder().addBaseCurrency(baseCurrency).addTargetCurrency(Currency.LTC).addPrice(priceGdaxLtcUsd).build());
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
                ", priceGdaxBchEur=" + priceGdaxBchEur +
                ", priceGdaxBchUsd=" + priceGdaxBchUsd +
                ", priceGdaxBtcEur=" + priceGdaxBtcEur +
                ", priceGdaxBtcUsd=" + priceGdaxBtcUsd +
                ", priceGdaxEthEur=" + priceGdaxEthEur +
                ", priceGdaxEthUsd=" + priceGdaxEthUsd +
                ", priceGdaxEtcEur=" + priceGdaxEtcEur +
                ", priceGdaxEtcUsd=" + priceGdaxEtcUsd +
                ", priceGdaxLtcEur=" + priceGdaxLtcEur +
                ", priceGdaxLtcUsd=" + priceGdaxLtcUsd +
                '}';
    }
}
