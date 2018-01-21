package dev.cytronix.cryptocurrency.util;

import java.text.NumberFormat;

public class PriceUtils {

    public static String format(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(price);
    }
}
