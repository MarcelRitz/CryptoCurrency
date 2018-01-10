package dev.cytronix.cryptocurrency.billing;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;

import java.util.ArrayList;
import java.util.List;

public class BillingRepository implements IBillingRepository, PurchasesUpdatedListener {

    private Activity activity;
    private OnBillingRepositoryListener listener;
    private BillingClient billingClient;
    private List<Purchase> purchases = new ArrayList<>();

    public BillingRepository(@NonNull Activity activity) {
        this.activity = activity;

        billingClient = new BillingClient.Builder(activity.getApplicationContext())
                .setListener(this)
                .build();
    }

    @Override
    public void setOnBillingRepositoryListener(OnBillingRepositoryListener listener) {
        this.listener = listener;
    }

    @Override
    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public void connect() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(Billing.SKU_DONATION_LOWEST);

                    List<Purchase> purchases = purchasesResult.getPurchasesList();
                    if(purchases == null) {
                        return;
                    }

                    BillingRepository.this.purchases = purchases;

                    if(listener != null) {
                        listener.onPurchased(BillingRepository.this.purchases);
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });
    }

    @Override
    public boolean isReady() {
        return billingClient.isReady();
    }

    @Override
    public void launchBilling(String sku, String type) {
        BillingFlowParams billingFlowParams = new BillingFlowParams.Builder()
                .setSku(sku)
                .setType(type)
                .build();

        billingClient.launchBillingFlow(activity, billingFlowParams);
    }

    @Override
    public void onPurchasesUpdated(int responseCode, List<Purchase> purchases) {
        if(purchases == null) {
            return;
        }

        this.purchases = purchases;

        if(listener != null) {
            listener.onPurchased(this.purchases);
        }
    }

    public interface OnBillingRepositoryListener {
        @SuppressWarnings("EmptyMethod")
        void onPurchased(List<Purchase> purchases);
    }
}
