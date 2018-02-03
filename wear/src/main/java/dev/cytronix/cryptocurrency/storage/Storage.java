package dev.cytronix.cryptocurrency.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.data.Currency;
import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.model.Price;

public class Storage implements IStorage {

    private static final String PRICE_LIST_SORT_DELIMETER = ",";
    private static final String PRICE_LIST_SORT_DEFAULT_VALUE = "";
    private SharedPreferences preferences;
    private Context context;

    public Storage(Context context) {
        this.context = context.getApplicationContext();

        init();
    }

    private void init() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    @Override
    public DataProvider getDataProvider() {
        String key = context.getString(R.string.preference_data_provider_key);
        String defaultValue = context.getString(R.string.preference_data_provider_default_value);
        String value = preferences.getString(key, defaultValue);
        return DataProvider.fromName(value);
    }

    @Override
    public String getCurrency() {
        String key = context.getString(R.string.preference_currency_key);
        String defaultValue = context.getString(R.string.preference_currency_default_value);
        return preferences.getString(key, defaultValue);
    }

    @Override
    public Long getComplicationInverval() {
        String key = context.getString(R.string.preference_complication_interval_key);
        String defaultValue = context.getString(R.string.preference_complication_interval_default_value);
        return preferences.getLong(key, Long.parseLong(defaultValue));
    }

    @Override
    public Long getComplicationInvervalLastTimestamp() {
        String key = context.getString(R.string.preference_complication_interval_last_timestamp_key);
        String defaultValue = context.getString(R.string.preference_complication_interval_last_timestamp_default_value);
        return preferences.getLong(key, Long.parseLong(defaultValue));
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void setComplicationInvervalLastTimestamp(long timestamp) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(context.getString(R.string.preference_complication_interval_last_timestamp_key), timestamp);
        editor.commit();
    }

    @Override
    public boolean showPriceListSortInfo() {
        String key = context.getString(R.string.preference_price_list_sort_info_key);
        boolean defaultValue = context.getResources().getBoolean(R.bool.preference_price_list_sort_info_default_value);
        return preferences.getBoolean(key, defaultValue);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void setPriceListSortInfo(boolean showed) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(context.getString(R.string.preference_price_list_sort_info_key), showed);
        editor.commit();
    }

    @Override
    public void updatePriceListSort(List<Price> prices) {
        String joined = preferences.getString(context.getString(R.string.preference_price_list_sort_key), PRICE_LIST_SORT_DEFAULT_VALUE);
        if (PRICE_LIST_SORT_DEFAULT_VALUE.equals(joined)) {
            return;
        }

        List<Price> temp = new ArrayList<>();
        for (String currency : joined.split(PRICE_LIST_SORT_DELIMETER)) {
            for (Price price : prices) {
                if (currency.equals(price.getTargetCurrency())) {
                    temp.add(price);
                    break;
                }
            }
        }

        if (temp.size() == 0) {
            return;
        }

        prices.clear();
        prices.addAll(temp);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void setPriceListSort(List<Price> prices) {
        StringJoiner joiner = new StringJoiner(PRICE_LIST_SORT_DELIMETER);
        for (Price price : prices) {
            joiner.add(price.getTargetCurrency());
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.preference_price_list_sort_key), joiner.toString());
        editor.commit();
    }

    @Override
    public void updatePriceListQuantity(List<Price> prices) {
        for (Price price : prices) {
            updatePriceQuantity(price);
        }
    }

    public void updatePriceQuantity(Price price) {
        switch (price.getTargetCurrency()) {
            case Currency.BCH:
                updatePriceBchQuantity(price);
                break;
            case Currency.ETH:
                updatePriceEthQuantity(price);
                break;
            case Currency.LTC:
                updatePriceLtcQuantity(price);
                break;
            case Currency.BTC:
            default:
                updatePriceBtcQuantity(price);
                break;
        }
    }

    private void updatePriceBchQuantity(Price price) {
        updatePriceQuantity(price, context.getString(R.string.preference_quantity_bch_key));
    }

    private void updatePriceBtcQuantity(Price price) {
        updatePriceQuantity(price, context.getString(R.string.preference_quantity_btc_key));
    }

    private void updatePriceEthQuantity(Price price) {
        updatePriceQuantity(price, context.getString(R.string.preference_quantity_eth_key));
    }

    private void updatePriceLtcQuantity(Price price) {
        updatePriceQuantity(price, context.getString(R.string.preference_quantity_ltc_key));
    }

    private void updatePriceQuantity(Price price, String key) {
        Double quantity;
        try {
            quantity =  Double.valueOf(preferences.getString(key, String.valueOf(Price.QUANTITY_DEFAULT)));
        } catch (NumberFormatException e) {
            quantity = Price.QUANTITY_DEFAULT;
        }
        price.setQuantity(quantity);
    }
}
