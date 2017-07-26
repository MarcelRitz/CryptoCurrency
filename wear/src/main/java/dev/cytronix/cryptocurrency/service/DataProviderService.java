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
import dev.cytronix.data.cryptocompare.model.ChfPrice;
import dev.cytronix.data.cryptocompare.model.Price;
import dev.cytronix.data.cryptocompare.model.UsdPrice;
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
        double value = 0;

        if(price instanceof ChfPrice) {
            value = ((ChfPrice) price).getValue();
        } else if(price instanceof UsdPrice) {
            value = ((UsdPrice) price).getValue();
        }

        ComplicationData.Builder builder;
        switch(dataType) {
            case ComplicationData.TYPE_RANGED_VALUE:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_RANGED_VALUE)
                        .setValue((float) value)
                        .setMinValue(0)
                        .setMaxValue(10)
                        .setShortText(ComplicationText.plainText(String.valueOf(value)));
                break;
            case ComplicationData.TYPE_SHORT_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                        .setShortText(ComplicationText.plainText(String.valueOf(value)));
                break;
            case ComplicationData.TYPE_LONG_TEXT:
                builder = new ComplicationData.Builder(ComplicationData.TYPE_LONG_TEXT)
                        .setLongText(ComplicationText.plainText(String.format(Locale.getDefault(), "%1$s: %2$f", price.getCurrency(), value)));
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