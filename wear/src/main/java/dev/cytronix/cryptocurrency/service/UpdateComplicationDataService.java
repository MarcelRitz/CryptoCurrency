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
    public static final String EXTRA_COMPLICATION_TYPE = "dev.cytronix.cryptocurrency.extra.COMPLICATION_TYPE";
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
        storage.setComplicationIntervalLocked(false, complicationId);

        DataProviderService.ComplicationType complicationType = (DataProviderService.ComplicationType) intent.getSerializableExtra(EXTRA_COMPLICATION_TYPE);
        complicationType = (complicationType == null) ? DataProviderService.ComplicationType.PRICE : complicationType;

        ComponentName componentName = new ComponentName(getApplicationContext(), getProviderService(currency, complicationType));
        ProviderUpdateRequester providerUpdateRequester = new ProviderUpdateRequester(getApplicationContext(), componentName);
        providerUpdateRequester.requestUpdate(complicationId);
    }

    private Class<?> getProviderService(String currency, DataProviderService.ComplicationType complicationType) {
        switch(currency) {
            case Currency.ALL:
                return AccountBalanceProviderService.class;
            case Currency.BCH:
                switch (complicationType) {
                    case WALLET:
                        return BchQuantityProviderService.class;
                    case PRICE:
                    default:
                        return BchProviderService.class;
                }
            case Currency.ETH:
                switch (complicationType) {
                    case WALLET:
                        return EthQuantityProviderService.class;
                    case PRICE:
                    default:
                        return EthProviderService.class;
                }
            case Currency.LTC:
                switch (complicationType) {
                    case WALLET:
                        return LtcQuantityProviderService.class;
                    case PRICE:
                    default:
                        return LtcProviderService.class;
                }
            case Currency.BTC:
            default:
                switch (complicationType) {
                    case WALLET:
                        return BtcQuantityProviderService.class;
                    case PRICE:
                    default:
                        return BtcProviderService.class;
                }
        }
    }
}