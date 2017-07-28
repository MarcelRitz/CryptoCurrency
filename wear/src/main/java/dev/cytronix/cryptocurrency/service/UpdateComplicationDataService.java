package dev.cytronix.cryptocurrency.service;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.support.wearable.complications.ProviderUpdateRequester;
import android.text.TextUtils;

import dev.cytronix.data.Currency;

public class UpdateComplicationDataService extends IntentService {

    public static final String ACTION_UPDATE_COMPLICATION = "dev.cytronix.cryptocurrency.action.UPDATE_COMPLICATION";
    public static final String EXTRA_COMPLICATION_ID = "dev.cytronix.cryptocurrency.extra.COMPLICATION_ID";
    public static final String EXTRA_CURRENCY = "dev.cytronix.cryptocurrency.extra.CURRENCY";
    public static final int COMPLICATION_ID_UNKNOWN = -1;

    public UpdateComplicationDataService() {
        super("UpdateComplicationDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
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

        ComponentName componentName = new ComponentName(getApplicationContext(), getProviderService(currency));
        ProviderUpdateRequester providerUpdateRequester = new ProviderUpdateRequester(getApplicationContext(), componentName);
        providerUpdateRequester.requestUpdate(complicationId);
    }

    private Class<?> getProviderService(String currency) {
        switch(currency) {
            case Currency.ETH:
                return EthProviderService.class;
            case Currency.LTC:
                return LtcProviderService.class;
            case Currency.BTC:
            default:
                return BtcProviderService.class;
        }
    }
}