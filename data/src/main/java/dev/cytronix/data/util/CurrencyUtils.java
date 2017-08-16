package dev.cytronix.data.util;

import dev.cytronix.data.Currency;

public class CurrencyUtils {

    public static String getCurrencySymbol(String currency) {
        switch (currency) {
            case Currency.EUR:
                return Currency.SYMBOL_EUR;
            case Currency.USD:
                return Currency.SYMBOL_USD;
            default:
                return "";
        }
    }
}
