package dev.cytronix.cryptocurrency.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.wearable.complications.ComplicationData;
import android.support.wearable.complications.ComplicationManager;
import android.support.wearable.complications.ComplicationProviderService;
import android.support.wearable.complications.ComplicationText;
import android.widget.Toast;

import java.util.Locale;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.presenter.IPricePresenter;
import dev.cytronix.data.presenter.PricePresenter;
import dev.cytronix.data.view.PriceView;

@SuppressLint("Registered")
public class DataProviderService extends ComplicationProviderService {

    private String toCurrency;

    public DataProviderService(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    private String getCurrency() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(getString(R.string.preference_currency_key), getString(R.string.preference_currency_default_value));
    }

    @Override
    public void onComplicationUpdate(final int complicationId, final int dataType, final ComplicationManager complicationManager) {
        IPricePresenter presenter = new PricePresenter(getCurrency(), new PriceView() {
            @Override
            public void onUpdate(Price price) {
                update(price, complicationId, dataType, complicationManager);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(DataProviderService.this, message, Toast.LENGTH_LONG).show();
            }
        });
        presenter.getData(toCurrency);
    }

    public void update(Price price, int complicationId, int dataType, ComplicationManager complicationManager) {
        ComplicationData.Builder builder;
        switch(dataType) {
            case ComplicationData.TYPE_SHORT_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                        .setShortText(ComplicationText.plainText(String.valueOf(price.getPrice())));
                break;
            case ComplicationData.TYPE_LONG_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_LONG_TEXT)
                        .setLongText(ComplicationText.plainText(String.format(Locale.getDefault(), "%1$s: %2$.2f", price.getTargetCurrency(), price.getPrice())));
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
}