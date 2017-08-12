package dev.cytronix.cryptocurrency.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.wearable.activity.WearableActivity;

@SuppressLint("Registered")
public class BaseActivity extends WearableActivity {

    protected void showFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment, tag).commit();
    }
}
