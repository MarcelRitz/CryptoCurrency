package dev.cytronix.cryptocurrency.service;

import android.support.wearable.complications.ComplicationManager;

import java.util.List;
import java.util.Locale;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.analytic.Fabric;
import dev.cytronix.cryptocurrency.util.FabricUtils;
import dev.cytronix.data.Currency;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.presenter.IPriceListPresenter;
import dev.cytronix.data.presenter.PriceListPresenter;
import dev.cytronix.data.util.CurrencyUtils;
import dev.cytronix.data.view.PriceListView;

public class AccountBalanceProviderService extends DataProviderService {

    public AccountBalanceProviderService() {
        super(Currency.ALL, ComplicationType.ACCOUNT_BALANCE);
    }

    @Override
    protected void processUpdate(int complicationId, int dataType, ComplicationManager complicationManager) {
        update(getString(R.string.all_loading), complicationId, dataType, complicationManager);

        IPriceListPresenter presenter = new PriceListPresenter(new PriceListView() {
            @Override
            public void onUpdate(List<Price> prices) {
                double value = 0.0;

                for(Price price:prices) {
                    storage.updatePriceQuantity(price);
                    value += price.getValue();
                }

                String shortText = String.format(Locale.getDefault(), getString(R.string.complication_text), CurrencyUtils.getCurrencySymbol(prices.get(0).getBaseCurrency()), value);

                update(getString(R.string.complication_my_total), shortText, complicationId, dataType, complicationManager);

                FabricUtils.trackEvent(Fabric.EVENT_COMPLICATION, Fabric.NAME_UPDATED, toCurrency);
            }

            @Override
            public void onError(String message) {
                update(getString(R.string.all_error), complicationId, dataType, complicationManager);
            }
        }, getProvider(), getCurrency());
        presenter.getData();
    }
}