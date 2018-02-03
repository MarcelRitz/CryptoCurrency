package dev.cytronix.cryptocurrency.service;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.support.wearable.complications.ProviderUpdateRequester;
import android.text.TextUtils;

import dev.cytronix.cryptocurrency.storage.IStorage;
import dev.cytronix.cryptocurrency.storage.Storage;
import dev.cytronix.data.Currency;

public class UpdateComplicationDataService extends IntentService {

    public static final String ACTION_UPDATE_COMPLICATION = "dev.cytronix.cryptocurrency.action.UPDATE_COMPLICATION";
    public static final String EXTRA_COMPLICATION_ID = "dev.cytronix.cryptocurrency.extra.COMPLICATION_ID";
    public static final String EXTRA_CURRENCY = "dev.cytronix.cryptocurrency.extra.CURRENCY";
    public static final String EXTRA_WALLET = "dev.cytronix.cryptocurrency.extra.WALLET";
    public static final int COMPLICATION_ID_UNKNOWN = -1;

    public UpdateComplicationDataService() {
        super("UpdateComplicationDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        switch (intent.getAction()) {
            case ACTION_UPDATE_COMPLICATION:
                updateComplication(intent);
                break;
        }
    }

    private void updateComplication(Intent intent) {
        int complicationId = intent.getIntExtra(EXTRA_COMPLICATION_ID, COMPLICATION_ID_UNKNOWN);
        if(COMPLICATION_ID_UNKNOWN == complicationId) {
            return;
        }

        String currency = intent.getStringExtra(EXTRA_CURRENCY);
        if(TextUtils.isEmpty(currency)) {
            return;
        }

        IStorage storage = new Storage(this);
        storage.setComplicationIntervalLocked(complicationId, false);

        ComponentName componentName = new ComponentName(getApplicationContext(), getProviderService(currency, intent.getBooleanExtra(EXTRA_WALLET, false)));
        ProviderUpdateRequester providerUpdateRequester = new ProviderUpdateRequester(getApplicationContext(), componentName);
        providerUpdateRequester.requestUpdate(complicationId);
    }

    private Class<?> getProviderService(String currency, boolean wallet) {
        switch(currency) {
            case Currency.BCH:
                return (wallet) ? BchQuantityProviderService.class : BchProviderService.class;
            case Currency.ETH:
                return (wallet) ? EthQuantityProviderService.class : EthProviderService.class;
            case Currency.LTC:
                return (wallet) ? LtcQuantityProviderService.class : LtcProviderService.class;
            case Currency.BTC:
            default:
                return (wallet) ? BtcQuantityProviderService.class : BtcProviderService.class;
        }
    }
}