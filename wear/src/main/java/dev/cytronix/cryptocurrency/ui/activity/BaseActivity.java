package dev.cytronix.cryptocurrency.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.wearable.activity.WearableActivity;

import dev.cytronix.cryptocurrency.ui.fragment.PriceListFragment;
import dev.cytronix.cryptocurrency.ui.fragment.SettingFragment;

@SuppressLint("Registered")
public class BaseActivity extends WearableActivity {

    protected void showFragment(String tag) {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment == null) {
            switch (tag) {
                case PriceListFragment.TAG:
                    fragment = PriceListFragment.newInstance();
                    break;
                case SettingFragment.TAG:
                    fragment = SettingFragment.newInstance();
                    break;
            }
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment, tag).commit();
    }
}
