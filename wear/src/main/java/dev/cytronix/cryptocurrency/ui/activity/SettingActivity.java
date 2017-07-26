package dev.cytronix.cryptocurrency.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import dev.cytronix.cryptocurrency.ui.fragment.SettingFragment;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFragment();
    }

    private void setFragment() {
        FragmentManager fragmentManager = getFragmentManager();

        SettingFragment fragment = new SettingFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment).commit();
    }
}
