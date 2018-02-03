package dev.cytronix.cryptocurrency.setting;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.wearable.complications.ProviderUpdateRequester;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.service.BchProviderService;
import dev.cytronix.cryptocurrency.service.BchQuantityProviderService;
import dev.cytronix.cryptocurrency.service.BtcProviderService;
import dev.cytronix.cryptocurrency.service.BtcQuantityProviderService;
import dev.cytronix.cryptocurrency.service.EthProviderService;
import dev.cytronix.cryptocurrency.service.EthQuantityProviderService;
import dev.cytronix.cryptocurrency.service.LtcProviderService;
import dev.cytronix.cryptocurrency.service.LtcQuantityProviderService;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = "SettingFragment";
    private SharedPreferences sharedPreferences;

    public static SettingFragment newInstance() {
        Bundle bundle = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void update(List<Class<?>> classes) {
        for(Class<?> cls:classes) {
            update(cls);
        }
    }

    private void update(Class<?> cls) {
        ComponentName componentName = new ComponentName(getContext(), cls);

        ProviderUpdateRequester providerUpdateRequester = new ProviderUpdateRequester(getContext(), componentName);
        providerUpdateRequester.requestUpdateAll();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(TextUtils.isEmpty(key)) {
            return;
        }

        if(key.equals(getString(R.string.preference_currency_key)) || key.equals(getString(R.string.preference_data_provider_key)) || key.equals(getString(R.string.preference_complication_interval_key))) {
            List<Class<?>> classes = new ArrayList<>();
            classes.add(BchProviderService.class);
            classes.add(BchQuantityProviderService.class);
            classes.add(BtcProviderService.class);
            classes.add(BtcQuantityProviderService.class);
            classes.add(EthProviderService.class);
            classes.add(EthQuantityProviderService.class);
            classes.add(LtcProviderService.class);
            classes.add(LtcQuantityProviderService.class);
            update(classes);
        } else if(key.equals(getString(R.string.preference_quantity_bch_key))) {
            update(BchQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_btc_key))) {
            update(BtcQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_eth_key))) {
            update(EthQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_ltc_key))) {
            update(LtcQuantityProviderService.class);
        }
    }
}
