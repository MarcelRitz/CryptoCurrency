package dev.cytronix.cryptocurrency.setting;

import android.os.Bundle;

import dev.cytronix.cryptocurrency.ui.activity.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(SettingFragment.TAG);
    }
}
