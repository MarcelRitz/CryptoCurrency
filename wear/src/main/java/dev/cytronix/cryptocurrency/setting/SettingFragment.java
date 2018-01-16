package dev.cytronix.cryptocurrency.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import dev.cytronix.cryptocurrency.R;

public class SettingFragment extends PreferenceFragment {

    public static final String TAG = "SettingFragment";

    public static SettingFragment newInstance() {
        Bundle bundle = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
