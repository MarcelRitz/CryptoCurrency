package dev.cytronix.data.cryptocompare.model;

import com.google.gson.annotations.SerializedName;

public class ChfPrice extends Price {

    @SerializedName(Currency.CHF)
    private double value;

    public ChfPrice() {
        super(Currency.CHF);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ChfPrice{" +
                "value=" + value +
                "} " + super.toString();
    }
}
