package dev.cytronix.cryptocurrency.ui.activity;

import android.os.Bundle;

import dev.cytronix.cryptocurrency.ui.fragment.SettingFragment;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(SettingFragment.TAG);
    }
}
