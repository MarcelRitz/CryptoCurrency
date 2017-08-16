package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

import dev.cytronix.data.Currency;

public class Price {

    public static final double PRICE_DEFAULT = -1.0;

    public static final int QUANTITY_BCH = 1;
    public static final int QUANTITY_BTC = 1;
    public static final int QUANTITY_ETH = 1;
    public static final int QUANTITY_LTC = 1;
    public static final int QUANTITY_DEFAULT = 1;

    private String baseCurrency;
    private String targetCurrency;

    @SerializedName("price")
    private double price = PRICE_DEFAULT;

    public Price() {
    }

    public Price(Builder builder) {
        this.baseCurrency = builder.baseCurrency;
        this.targetCurrency = builder.targetCurrency;
        this.price = builder.price;
    }

    public int getQuantity() {
        switch (baseCurrency) {
            case Currency.BCH:
                return QUANTITY_BCH;
            case Currency.BTC:
                return QUANTITY_BTC;
            case Currency.ETH:
                return QUANTITY_ETH;
            case Currency.LTC:
                return QUANTITY_LTC;
            default:
                return QUANTITY_DEFAULT;
        }
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                ", price=" + price +
                '}';
    }

    public static class Builder {

        private String baseCurrency;
        private String targetCurrency;
        private double price = -1.0;

        public Builder addBaseCurrency(String baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder addTargetCurrency(String targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public Builder addPrice(double price) {
            this.price = price;
            return this;
        }

        public Price build() {
            return new Price(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "baseCurrency='" + baseCurrency + '\'' +
                    ", targetCurrency='" + targetCurrency + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
