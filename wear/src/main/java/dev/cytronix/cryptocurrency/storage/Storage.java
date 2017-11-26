package dev.cytronix.cryptocurrency.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.data.cryptowat.model.DataProvider;

public class Storage implements IStorage {

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
}
