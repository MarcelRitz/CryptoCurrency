package dev.cytronix.cryptocurrency.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.ui.activity.SettingActivity;

public class MainFragment extends BaseFragment implements MenuItem.OnMenuItemClickListener {

    public static final String TAG = "MainFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);

        initLayout(viewRoot);

        return viewRoot;
    }

    private void initLayout(View viewRoot) {
        WearableActionDrawerView actionDrawer = viewRoot.findViewById(R.id.wearableactiondrawerview_main_action);
        actionDrawer.getController().peekDrawer();
        actionDrawer.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_settings:
            default:
                startActivity(new Intent(getContext(), SettingActivity.class));
                return true;
        }
    }
}
