package dev.cytronix.cryptocurrency.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.wearable.complications.ComplicationData;
import android.support.wearable.complications.ComplicationManager;
import android.support.wearable.complications.ComplicationProviderService;
import android.support.wearable.complications.ComplicationText;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;
import java.util.Locale;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.analytic.Analytics;
import dev.cytronix.cryptocurrency.analytic.Fabric;
import dev.cytronix.cryptocurrency.storage.IStorage;
import dev.cytronix.cryptocurrency.storage.Storage;
import dev.cytronix.cryptocurrency.util.AnalyticsUtils;
import dev.cytronix.cryptocurrency.util.FabricUtils;
import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.presenter.IPricePresenter;
import dev.cytronix.data.presenter.PricePresenter;
import dev.cytronix.data.util.CurrencyUtils;
import dev.cytronix.data.view.PriceView;

@SuppressLint("Registered")
public class DataProviderService extends ComplicationProviderService {

    public enum ComplicationType {PRICE, WALLET, ACCOUNT_BALANCE}
    private static final long TIMESTAMP_OFFSET = 10000L;
    protected String toCurrency;
    protected ComplicationType complicationType;
    protected IStorage storage;

    public DataProviderService(String toCurrency, ComplicationType complicationType) {
        super();

        this.toCurrency = toCurrency;
        this.complicationType = complicationType;
    }

    @Override
    public IBinder onBind(Intent intent) {
        storage = new Storage(this);
        return super.onBind(intent);
    }

    protected DataProvider getProvider() {
        return storage.getDataProvider();
    }

    protected String getCurrency() {
        return storage.getCurrency();
    }

    @Override
    public void onComplicationActivated(int complicationId, int type, ComplicationManager manager) {
        super.onComplicationActivated(complicationId, type, manager);

        storage.removeComplicationInvervalLastTimestamp(complicationId);
        storage.removeComplicationIntervalLocked(complicationId);

        FabricUtils.trackEvent(Fabric.EVENT_COMPLICATION, Fabric.NAME_ACTIVATED, 1.0f);
        AnalyticsUtils.trackEvent(this, FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_COMPLICATION_ACTIVATED, toCurrency, 1);
    }

    @Override
    public void onComplicationUpdate(int complicationId, int dataType, ComplicationManager complicationManager) {
        boolean locked = storage.getComplicationIntervalLocked(complicationId);
        if(!locked) {
            processUpdate(complicationId, dataType, complicationManager);
            storage.setComplicationIntervalLocked(true, complicationId);
            return;
        }

        long lastTimestamp = storage.getComplicationInvervalLastTimestamp(complicationId);
        long inverval = storage.getComplicationInverval();
        long currentTimestamp = System.currentTimeMillis() - TIMESTAMP_OFFSET;
        if(lastTimestamp + inverval < currentTimestamp) {
            processUpdate(complicationId, dataType, complicationManager);
            storage.setComplicationInvervalLastTimestamp(complicationId, System.currentTimeMillis());
        }
    }

    protected void processUpdate(int complicationId, int dataType, ComplicationManager complicationManager) {
        update(getString(R.string.all_loading), complicationId, dataType, complicationManager);

        IPricePresenter presenter = new PricePresenter(getProvider(), getCurrency(), new PriceView() {
            @Override
            public void onUpdate(List<Price> prices) {
                Price price = prices.get(0);

                double value;
                String title;
                switch (complicationType) {
                    case WALLET:
                        storage.updatePriceQuantity(price);
                        value = price.getValue();
                        title = getString(R.string.complication_wallet) + " " + price.getTargetCurrency();
                        break;
                    case PRICE:
                    default:
                        value = price.getPrice();
                        title = price.getTargetCurrency();
                        break;
                }

                String format = getString((storage.isComplicationCurrencyCent()) ? R.string.complication_text_cent : R.string.complication_text);
                String shortText = String.format(Locale.getDefault(), format, CurrencyUtils.getCurrencySymbol(price.getBaseCurrency()), value);

                update(title, shortText, complicationId, dataType, complicationManager);

                FabricUtils.trackEvent(Fabric.EVENT_COMPLICATION, Fabric.NAME_UPDATED, price.getTargetCurrency());
            }

            @Override
            public void onError(String message) {
                update(getString(R.string.all_error), complicationId, dataType, complicationManager);
            }
        });
        presenter.getData(toCurrency);
    }

    protected void update(String text, int complicationId, int dataType, ComplicationManager complicationManager) {
        update("", text, complicationId, dataType, complicationManager);
    }

    protected void update(String title, String text, int complicationId, int dataType, ComplicationManager complicationManager) {
        ComplicationData.Builder builder;
        switch(dataType) {
            case ComplicationData.TYPE_SHORT_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                        .setShortText(ComplicationText.plainText(text));

                if(storage.isComplicationCurrencyTitle()) {
                        builder.setShortTitle(ComplicationText.plainText(title));
                }
                break;
            case ComplicationData.TYPE_LONG_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_LONG_TEXT)
                        .setLongText(ComplicationText.plainText(text));

                if(storage.isComplicationCurrencyTitle()) {
                        builder.setLongTitle(ComplicationText.plainText(title));
                }
                break;
            default:
                return;
        }

        Intent updateIntent = new Intent(getApplicationContext(), UpdateComplicationDataService.class);
        updateIntent.setAction(UpdateComplicationDataService.ACTION_UPDATE_COMPLICATION);
        updateIntent.putExtra(UpdateComplicationDataService.EXTRA_COMPLICATION_ID, complicationId);
        updateIntent.putExtra(UpdateComplicationDataService.EXTRA_CURRENCY, toCurrency);
        updateIntent.putExtra(UpdateComplicationDataService.EXTRA_COMPLICATION_TYPE, complicationType);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), complicationId, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setTapAction(pendingIntent);

        complicationManager.updateComplicationData(complicationId, builder.build());
    }

    @Override
    public void onComplicationDeactivated(int complicationId) {
        super.onComplicationDeactivated(complicationId);

        FabricUtils.trackEvent(Fabric.EVENT_COMPLICATION, Fabric.NAME_DEACTIVATED, 1.0f);
        AnalyticsUtils.trackEvent(this, FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_COMPLICATION_DEACTIVATED, toCurrency, 1);
    }
}