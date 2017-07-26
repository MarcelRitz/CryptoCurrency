package dev.cytronix.data.cryptocompare.model;

import com.google.gson.annotations.SerializedName;

public class UsdPrice extends Price {

    @SerializedName(Currency.USD)
    private double value;

    public UsdPrice() {
        super(Currency.USD);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "UsdPrice{" +
                "value=" + value +
                "} " + super.toString();
    }
}
