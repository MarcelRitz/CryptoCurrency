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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.adapter.CurrencyAdapter;
import dev.cytronix.cryptocurrency.analytic.Analytics;
import dev.cytronix.cryptocurrency.billing.Billing;
import dev.cytronix.cryptocurrency.storage.Storage;
import dev.cytronix.cryptocurrency.ui.activity.SettingActivity;
import dev.cytronix.cryptocurrency.util.AnalyticsUtils;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.presenter.IPriceListPresenter;
import dev.cytronix.data.presenter.PriceListPresenter;
import dev.cytronix.data.view.PriceListView;

public class PriceListFragment extends BaseFragment implements PriceListView, MenuItem.OnMenuItemClickListener, View.OnClickListener, PurchasesUpdatedListener {

    public static final String TAG = "PriceListFragment";
    private BillingClient billingClient;
    private IPriceListPresenter presenter;
    private CurrencyAdapter adapter;
    private Storage storage;
    private TextView textViewError;
    private LinearLayout linearLayoutLoading;
    private WearableRecyclerView recyclerView;
    private WearableActionDrawerView actionDrawer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_pricelist, container, false);

        storage = new Storage(getContext());
        presenter = new PriceListPresenter(this, storage.getCurrency());

        initLayout(viewRoot);
        initBilling();

        return viewRoot;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(actionDrawer.isOpened()) {
            actionDrawer.getController().closeDrawer();
        }

        presenter.setBaseCurrency(storage.getCurrency());
        refresh();
    }

    private void initLayout(View viewRoot) {
        textViewError = viewRoot.findViewById(R.id.textview_pricelist_error);
        textViewError.setOnClickListener(this);

        linearLayoutLoading = viewRoot.findViewById(R.id.linearlayout_pricelist_loading);

        recyclerView = viewRoot.findViewById(R.id.wearablerecyclerview_pricelist_pricelist);
        setPriceList(recyclerView);

        actionDrawer = viewRoot.findViewById(R.id.wearableactiondrawerview_main_action);
        actionDrawer.getController().peekDrawer();
        actionDrawer.setOnMenuItemClickListener(this);
    }

    private void setPriceList(WearableRecyclerView recyclerView) {
        adapter = new CurrencyAdapter(presenter.getPrices());

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
                    // ignore
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

    private void refresh() {
        textViewError.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.VISIBLE);

        presenter.getData();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_donation:
                showDonation();
                return true;
            case R.id.menu_refresh:
                refresh();
                actionDrawer.getController().closeDrawer();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_pricelist_error:
            default:
                refresh();
                break;
        }
    }

    @Override
    public void onUpdate(List<Price> prices) {
        if(!isAdded()) {
            return;
        }

        adapter.notifyDataSetChanged();

        linearLayoutLoading.setVisibility(View.GONE);
        textViewError.setVisibility(View.GONE);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        if(!isAdded()) {
            return;
        }

        linearLayoutLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        textViewError.setVisibility(View.VISIBLE);
    }
}
