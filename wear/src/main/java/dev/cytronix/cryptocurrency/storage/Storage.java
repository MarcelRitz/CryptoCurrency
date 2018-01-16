package dev.cytronix.cryptocurrency.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import dev.cytronix.cryptocurrency.R;
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
    public void updatePriceListSort(List<Price> prices) {
        String joined = preferences.getString(context.getString(R.string.preference_price_list_sort_key), PRICE_LIST_SORT_DEFAULT_VALUE);
        if(PRICE_LIST_SORT_DEFAULT_VALUE.equals(joined)) {
            return;
        }

        List<Price> temp = new ArrayList<>();
        for(String currency:joined.split(PRICE_LIST_SORT_DELIMETER)) {
            for(Price price:prices) {
                if(currency.equals(price.getTargetCurrency())) {
                    temp.add(price);
                    break;
                }
            }
        }

        if(temp.size() == 0) {
            return;
        }

        prices.clear();
        prices.addAll(temp);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void setPriceListSort(List<Price> prices) {
        StringJoiner joiner = new StringJoiner(PRICE_LIST_SORT_DELIMETER);
        for(Price price:prices) {
            joiner.add(price.getTargetCurrency());
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.preference_price_list_sort_key), joiner.toString());
        editor.commit();
    }
}
