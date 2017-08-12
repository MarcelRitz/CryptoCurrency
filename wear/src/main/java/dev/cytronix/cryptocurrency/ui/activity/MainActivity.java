package dev.cytronix.cryptocurrency.ui.activity;

import android.os.Bundle;

import dev.cytronix.cryptocurrency.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(new MainFragment(), MainFragment.TAG);
    }
}
