package dev.cytronix.cryptocurrency.billing;

import com.android.billingclient.api.Purchase;

import java.util.List;

public interface IBillingRepository {

    void setOnBillingRepositoryListener(BillingRepository.OnBillingRepositoryListener listener);

    List<Purchase> getPurchases();

    void connect();

    boolean isReady();

    void launchBilling(String sku, String type);
}
