package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

public class Price {

    public static final double PRICE_DEFAULT = -1.0;
    public static final double QUANTITY_DEFAULT = 0.0;

    private String baseCurrency;
    private String targetCurrency;

    @SerializedName("price")
    private double price = PRICE_DEFAULT;

    private double quantity = QUANTITY_DEFAULT;

    public Price() {
    }

    public Price(Builder builder) {
        this.baseCurrency = builder.baseCurrency;
        this.targetCurrency = builder.targetCurrency;
        this.price = builder.price;
        this.quantity = builder.quantitiy;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {

        private String baseCurrency;
        private String targetCurrency;
        private double price = PRICE_DEFAULT;
        private double quantitiy = QUANTITY_DEFAULT;

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

        public Builder addQuantity(double quantity) {
            this.quantitiy = quantity;
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
                    ", quantitiy=" + quantitiy +
                    '}';
        }
    }
}
