package dev.cytronix.cryptocurrency.ui.activity;

import android.os.Bundle;

import dev.cytronix.cryptocurrency.ui.fragment.PriceListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(PriceListFragment.TAG);
    }
}
