package dev.cytronix.cryptocurrency.pricelist;

import android.os.Bundle;
import android.preference.PreferenceManager;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_App);
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        showFragment(PriceListFragment.TAG);
    }
}
