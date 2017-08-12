package dev.cytronix.cryptocurrency.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.analytic.Analytics;
import dev.cytronix.cryptocurrency.billing.Billing;
import dev.cytronix.cryptocurrency.ui.activity.SettingActivity;
import dev.cytronix.cryptocurrency.util.AnalyticsUtils;

public class MainFragment extends BaseFragment implements MenuItem.OnMenuItemClickListener, View.OnClickListener, PurchasesUpdatedListener {

    public static final String TAG = "MainFragment";
    private Button buttonDonation;
    private BillingClient billingClient;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);

        initLayout(viewRoot);
        initBilling();

        return viewRoot;
    }

    private void initLayout(View viewRoot) {
        buttonDonation = viewRoot.findViewById(R.id.button_main_donation);
        buttonDonation.setOnClickListener(this);

        WearableActionDrawerView actionDrawer = viewRoot.findViewById(R.id.wearableactiondrawerview_main_action);
        actionDrawer.getController().peekDrawer();
        actionDrawer.setOnMenuItemClickListener(this);
    }

    private void initBilling() {
        billingClient = new BillingClient.Builder(getContext()).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    buttonDonation.setEnabled(true);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });
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

    @Override
    public void onClick(View view) {
        BillingFlowParams billingFlowParams = new BillingFlowParams.Builder()
                .setSku(Billing.SKU_DONATION_LOWEST).setType(BillingClient.SkuType.INAPP)
                .build();

        billingClient.launchBillingFlow(getActivity(), billingFlowParams);

        AnalyticsUtils.trackEvent(getContext(), FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_DONATION, Analytics.ITEM_NAME_DONATION, 1);
    }

    @Override
    public void onPurchasesUpdated(int responseCode, List<Purchase> purchases) {
        // ignore
    }
}
