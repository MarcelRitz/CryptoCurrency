package dev.cytronix.cryptocurrency.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.adapter.CurrencyAdapter;
import dev.cytronix.cryptocurrency.analytic.Analytics;
import dev.cytronix.cryptocurrency.billing.Billing;
import dev.cytronix.cryptocurrency.ui.activity.SettingActivity;
import dev.cytronix.cryptocurrency.util.AnalyticsUtils;
import dev.cytronix.data.cryptowat.model.Price;

public class MainFragment extends BaseFragment implements MenuItem.OnMenuItemClickListener, PurchasesUpdatedListener {

    public static final String TAG = "MainFragment";
    private BillingClient billingClient;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);

        initLayout(viewRoot);
        initBilling();

        return viewRoot;
    }

    private void initLayout(View viewRoot) {
        setListCurrency((WearableRecyclerView) viewRoot.findViewById(R.id.wearablerecyclerview_main_currency));

        WearableActionDrawerView actionDrawer = viewRoot.findViewById(R.id.wearableactiondrawerview_main_action);
        actionDrawer.getController().peekDrawer();
        actionDrawer.setOnMenuItemClickListener(this);
    }

    private void setListCurrency(WearableRecyclerView recyclerView) {
        List<Price> prices = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Price price = new Price();
            price.setBaseCurrency("Base"+i);
            price.setTargetCurrency("Target"+i);

            prices.add(price);
        }

        CurrencyAdapter adapter = new CurrencyAdapter(prices);

        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initBilling() {
        billingClient = new BillingClient.Builder(getContext()).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });
    }

    private void showDonation() {
        if(!billingClient.isReady()) {
            return;
        }

        BillingFlowParams billingFlowParams = new BillingFlowParams.Builder()
                .setSku(Billing.SKU_DONATION_LOWEST).setType(BillingClient.SkuType.INAPP)
                .build();

        billingClient.launchBillingFlow(getActivity(), billingFlowParams);

        AnalyticsUtils.trackEvent(getContext(), FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_DONATION, Analytics.ITEM_NAME_DONATION, 1);
    }

    private void showSettings() {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_donation:
                showDonation();
                return true;
            case R.id.menu_settings:
            default:
                showSettings();
                return true;
        }
    }

    @Override
    public void onPurchasesUpdated(int responseCode, List<Purchase> purchases) {
        // ignore
    }
}
