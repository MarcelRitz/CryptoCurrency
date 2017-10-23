package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

public class ResultPrices {

    @SerializedName("result")
    private Prices prices = new Prices();

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "ResultPrices{" +
                "prices=" + prices +
                '}';
    }
}
