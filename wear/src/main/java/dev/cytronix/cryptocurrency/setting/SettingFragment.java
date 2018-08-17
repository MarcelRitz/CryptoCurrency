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
import java.util.stream.IntStream;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.service.AccountBalanceProviderService;
import dev.cytronix.cryptocurrency.service.BchProviderService;
import dev.cytronix.cryptocurrency.service.BchQuantityProviderService;
import dev.cytronix.cryptocurrency.service.BtcProviderService;
import dev.cytronix.cryptocurrency.service.BtcQuantityProviderService;
import dev.cytronix.cryptocurrency.service.EtcProviderService;
import dev.cytronix.cryptocurrency.service.EtcQuantityProviderService;
import dev.cytronix.cryptocurrency.service.EthProviderService;
import dev.cytronix.cryptocurrency.service.EthQuantityProviderService;
import dev.cytronix.cryptocurrency.service.LtcProviderService;
import dev.cytronix.cryptocurrency.service.LtcQuantityProviderService;
import dev.cytronix.cryptocurrency.storage.IStorage;
import dev.cytronix.cryptocurrency.storage.Storage;

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

    private void updateComplication(List<Class<?>> classes) {
        IStorage storage = new Storage(getContext());
        storage.setComplicationIntervalLocked( false, IntStream.range(0, 100).toArray());

        for(Class<?> cls:classes) {
            ComponentName componentName = new ComponentName(getContext(), cls);
            ProviderUpdateRequester providerUpdateRequester = new ProviderUpdateRequester(getContext(), componentName);
            providerUpdateRequester.requestUpdateAll();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(TextUtils.isEmpty(key)) {
            return;
        }

        List<String> keys = new ArrayList<>();
        keys.add(getString(R.string.preference_currency_key));
        keys.add(getString(R.string.preference_data_provider_key));
        keys.add(getString(R.string.preference_complication_interval_key));
        keys.add(getString(R.string.preference_complication_currency_title_key));
        keys.add(getString(R.string.preference_complication_currency_cent_key));
        keys.add(getString(R.string.preference_quantity_bch_key));
        keys.add(getString(R.string.preference_quantity_btc_key));
        keys.add(getString(R.string.preference_quantity_eth_key));
        keys.add(getString(R.string.preference_quantity_etc_key));
        keys.add(getString(R.string.preference_quantity_ltc_key));
        if(!keys.contains(key)) {
            return;
        }

        List<Class<?>> classes = new ArrayList<>();
        classes.add(AccountBalanceProviderService.class);

        if(key.equals(getString(R.string.preference_currency_key)) || key.equals(getString(R.string.preference_data_provider_key)) || key.equals(getString(R.string.preference_complication_interval_key)) || key.equals(getString(R.string.preference_complication_currency_title_key)) || key.equals(getString(R.string.preference_complication_currency_cent_key))) {
            classes.add(BchProviderService.class);
            classes.add(BchQuantityProviderService.class);
            classes.add(BtcProviderService.class);
            classes.add(BtcQuantityProviderService.class);
            classes.add(EthProviderService.class);
            classes.add(EthQuantityProviderService.class);
            classes.add(EtcProviderService.class);
            classes.add(EtcQuantityProviderService.class);
            classes.add(LtcProviderService.class);
            classes.add(LtcQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_bch_key))) {
            classes.add(BchQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_btc_key))) {
            classes.add(BtcQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_eth_key))) {
            classes.add(EthQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_etc_key))) {
            classes.add(EtcQuantityProviderService.class);
        } else if(key.equals(getString(R.string.preference_quantity_ltc_key))) {
            classes.add(LtcQuantityProviderService.class);
        }

        updateComplication(classes);
    }
}
