package dev.cytronix.data.cryptowat.model;

import com.google.gson.annotations.SerializedName;

public class ResultPrice {

    @SerializedName("result")
    private Price price = new Price();

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ResultPrice{" +
                "price=" + price +
                '}';
    }
}
