package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("result")
    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Result{" +
                "price=" + price +
                '}';
    }
}