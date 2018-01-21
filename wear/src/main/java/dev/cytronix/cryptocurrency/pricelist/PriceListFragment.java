package dev.cytronix.cryptocurrency.pricelist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Collections;
import java.util.List;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.adapter.CurrencyAdapter;
import dev.cytronix.cryptocurrency.analytic.Analytics;
import dev.cytronix.cryptocurrency.analytic.Fabric;
import dev.cytronix.cryptocurrency.billing.Billing;
import dev.cytronix.cryptocurrency.billing.BillingRepository;
import dev.cytronix.cryptocurrency.billing.IBillingRepository;
import dev.cytronix.cryptocurrency.databinding.FragmentPricelistBinding;
import dev.cytronix.cryptocurrency.setting.SettingActivity;
import dev.cytronix.cryptocurrency.storage.Storage;
import dev.cytronix.cryptocurrency.ui.fragment.BaseFragment;
import dev.cytronix.cryptocurrency.util.AnalyticsUtils;
import dev.cytronix.cryptocurrency.util.AppStoreUtils;
import dev.cytronix.cryptocurrency.util.FabricUtils;
import dev.cytronix.cryptocurrency.util.IntentUtils;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.presenter.IPriceListPresenter;
import dev.cytronix.data.presenter.PriceListPresenter;
import dev.cytronix.data.view.PriceListView;

public class PriceListFragment extends BaseFragment implements PriceListView, MenuItem.OnMenuItemClickListener, View.OnClickListener, BillingRepository.OnBillingRepositoryListener {

    public static final String TAG = "PriceListFragment";

    public enum Status {UNKNOWN, DATA, LOADING, ERROR}

    private FragmentPricelistBinding binding;
    private WearableRecyclerView recyclerView;
    private WearableActionDrawerView actionDrawer;
    private Storage storage;
    private IPriceListPresenter presenter;
    private IBillingRepository billingRepository;
    private CurrencyAdapter adapter;
    public ObservableField<Status> status = new ObservableField<>(Status.UNKNOWN);

    public static PriceListFragment newInstance() {
        Bundle bundle = new Bundle();

        PriceListFragment fragment = new PriceListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pricelist, container, false);
        binding.setFragment(this);

        storage = new Storage(getContext());
        presenter = new PriceListPresenter(this, storage.getDataProvider(), storage.getCurrency());

        initBilling();
        initLayout();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setDataProvider(storage.getDataProvider());
        presenter.setBaseCurrency(storage.getCurrency());
        refresh();
    }

    private void initBilling() {
        billingRepository = new BillingRepository(getActivity());
        billingRepository.setOnBillingRepositoryListener(this);
        billingRepository.connect();
    }

    private void initLayout() {
        binding.textviewPricelistError.setOnClickListener(this);

        recyclerView = binding.wearablerecyclerviewPricelistPricelist;
        setPriceList(recyclerView);

        actionDrawer = binding.wearableactiondrawerviewMainAction;
        actionDrawer.getController().peekDrawer();
        actionDrawer.setOnMenuItemClickListener(this);
    }

    private void setPriceList(WearableRecyclerView recyclerView) {
        adapter = new CurrencyAdapter(presenter.getPrices());

        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(presenter.getPrices(), i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(presenter.getPrices(), i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                storage.setPriceListSort(presenter.getPrices());
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    private void actionRefresh() {
        refresh();
        actionDrawer.getController().closeDrawer();

        FabricUtils.trackEvent(Fabric.EVENT_MENU, Fabric.MENU_REFRESH, 1.0f);
    }

    private void actionDonation() {
        if(!billingRepository.isReady()) {
            return;
        }

        billingRepository.launchBilling(Billing.SKU_DONATION_LOWEST, BillingClient.SkuType.INAPP);

        FabricUtils.trackEvent(Fabric.EVENT_MENU, Fabric.MENU_DONATION, 1.0f);
        AnalyticsUtils.trackEvent(getContext(), FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_DONATION, Analytics.ITEM_NAME_DONATION, 1);
    }

    private void actionShare() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, AppStoreUtils.getWebLink(getContext().getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        if(IntentUtils.isAvailable(getContext(), intent)) {
            startActivity(Intent.createChooser(intent, getString(R.string.menu_share)));
        }

        FabricUtils.trackEvent(Fabric.EVENT_MENU, Fabric.MENU_SHARE, 1.0f);
    }

    private void actionRating() {
        Intent intent = AppStoreUtils.getAppIntent(getContext().getPackageName());
        if(IntentUtils.isAvailable(getContext(), intent)) {
            startActivity(intent);
        }

        FabricUtils.trackEvent(Fabric.EVENT_MENU, Fabric.MENU_RATING, 1.0f);
    }

    private void actionSettings() {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);

        FabricUtils.trackEvent(Fabric.EVENT_MENU, Fabric.MENU_SETTINGS, 1.0f);
    }

    private void refresh() {
        if(Status.LOADING == status.get()) {
            return;
        }
        status.set(Status.LOADING);

        presenter.getData();
    }

    @Override
    public void onUpdate(List<Price> prices) {
        if(!isAdded()) {
            return;
        }

        storage.updatePriceListSort(prices);
        storage.updatePriceListQuantity(prices);

        status.set(Status.DATA);

        adapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(0);

        if(storage.showPriceListSortInfo()) {
            showToast(R.string.price_list_sort_info);
            storage.setPriceListSortInfo(false);
        }
    }

    @Override
    public void onError(String message) {
        if(!isAdded()) {
            return;
        }

        status.set(Status.ERROR);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_refresh:
                actionRefresh();
                return true;
            case R.id.menu_donation:
                actionDonation();
                return true;
            case R.id.menu_share:
                actionShare();
                return true;
            case R.id.menu_rating:
                actionRating();
                return true;
            case R.id.menu_settings:
            default:
                actionSettings();
                return true;
        }
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
    public void onPurchased(List<Purchase> purchases) {
    }
}
