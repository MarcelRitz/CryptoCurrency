package dev.cytronix.cryptocurrency.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.wearable.complications.ComplicationData;
import android.support.wearable.complications.ComplicationManager;
import android.support.wearable.complications.ComplicationProviderService;
import android.support.wearable.complications.ComplicationText;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;
import java.util.Locale;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.analytic.Analytics;
import dev.cytronix.cryptocurrency.storage.Storage;
import dev.cytronix.cryptocurrency.util.AnalyticsUtils;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.cryptowat.model.DataProvider;
import dev.cytronix.data.presenter.IPricePresenter;
import dev.cytronix.data.presenter.PricePresenter;
import dev.cytronix.data.util.CurrencyUtils;
import dev.cytronix.data.view.PriceView;

@SuppressLint("Registered")
public class DataProviderService extends ComplicationProviderService {

    private String toCurrency;

    public DataProviderService(String toCurrency) {
        super();

        this.toCurrency = toCurrency;
    }

    private DataProvider getProvider() {
        return new Storage(this).getDataProvider();
    }

    private String getCurrency() {
        return new Storage(this).getCurrency();
    }

    @Override
    public void onComplicationActivated(int complicationId, int type, ComplicationManager manager) {
        super.onComplicationActivated(complicationId, type, manager);

        AnalyticsUtils.trackEvent(this, FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_COMPLICATION_ACTIVATED, toCurrency, 1);
    }

    @Override
    public void onComplicationUpdate(final int complicationId, final int dataType, final ComplicationManager complicationManager) {
        update(getString(R.string.all_loading), complicationId, dataType, complicationManager);

        IPricePresenter presenter = new PricePresenter(getProvider(), getCurrency(), new PriceView() {
            @Override
            public void onUpdate(List<Price> prices) {
                Price price = prices.get(0);

                String shortText = String.format(Locale.getDefault(), getString(R.string.complication_text), CurrencyUtils.getCurrencySymbol(price.getBaseCurrency()), price.getPrice());

                update(price.getTargetCurrency(), shortText, complicationId, dataType, complicationManager);
            }

            @Override
            public void onError(String message) {
                update(getString(R.string.all_error), complicationId, dataType, complicationManager);
            }
        });
        presenter.getData(toCurrency);
    }

    public void update(String text, int complicationId, int dataType, ComplicationManager complicationManager) {
        update("", text, complicationId, dataType, complicationManager);
    }

    public void update(String title, String text, int complicationId, int dataType, ComplicationManager complicationManager) {
        ComplicationData.Builder builder;
        switch(dataType) {
            case ComplicationData.TYPE_SHORT_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                        .setShortTitle(ComplicationText.plainText(title))
                        .setShortText(ComplicationText.plainText(text));
                break;
            case ComplicationData.TYPE_LONG_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_LONG_TEXT)
                        .setLongTitle(ComplicationText.plainText(title))
                        .setLongText(ComplicationText.plainText(text));
                break;
            default:
                return;
        }

        Intent updateIntent = new Intent(getApplicationContext(), UpdateComplicationDataService.class);
        updateIntent.setAction(UpdateComplicationDataService.ACTION_UPDATE_COMPLICATION);
        updateIntent.putExtra(UpdateComplicationDataService.EXTRA_COMPLICATION_ID, complicationId);
        updateIntent.putExtra(UpdateComplicationDataService.EXTRA_CURRENCY, toCurrency);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), complicationId, updateIntent, 0);
        builder.setTapAction(pendingIntent);

        complicationManager.updateComplicationData(complicationId, builder.build());
    }

    @Override
    public void onComplicationDeactivated(int complicationId) {
        super.onComplicationDeactivated(complicationId);

        AnalyticsUtils.trackEvent(this, FirebaseAnalytics.Event.SELECT_CONTENT, Analytics.ITEM_ID_COMPLICATION_DEACTIVATED, toCurrency, 1);
    }
}