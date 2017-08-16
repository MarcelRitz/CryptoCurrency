package dev.cytronix.cryptocurrency.adapter;

import android.content.Context;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.data.cryptowat.model.Price;
import dev.cytronix.data.util.CurrencyUtils;

public class CurrencyAdapter extends WearableRecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<Price> prices;

    public CurrencyAdapter(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public CurrencyAdapter.CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new CurrencyAdapter.CurrencyViewHolder(viewRoot);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        Price price = prices.get(position);

        Context context = holder.getContext();

        String baseText = String.format(Locale.getDefault(), context.getString(R.string.item_price_currency_base), price.getQuantity(), price.getTargetCurrency());
        holder.getTextViewBase().setText(baseText);

        String targetText;
        if(Price.PRICE_DEFAULT == price.getPrice()) {
            targetText = String.format(Locale.getDefault(), context.getString(R.string.item_price_currency_target_empty), CurrencyUtils.getCurrencySymbol(price.getBaseCurrency()));
        } else {
            targetText = String.format(Locale.getDefault(), context.getString(R.string.item_price_currency_target), price.getPrice(), CurrencyUtils.getCurrencySymbol(price.getBaseCurrency()));
        }
        holder.getTextViewTarget().setText(targetText);
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class CurrencyViewHolder extends WearableRecyclerView.ViewHolder {

        private TextView textViewBase;
        private TextView textViewTarget;

        public CurrencyViewHolder(View viewRoot) {
            super(viewRoot);

            textViewBase = viewRoot.findViewById(R.id.textview_itemcurrency_baseCurrency);
            textViewTarget = viewRoot.findViewById(R.id.textview_itemcurrency_targetCurrency);
        }

        public TextView getTextViewBase() {
            return textViewBase;
        }

        public TextView getTextViewTarget() {
            return textViewTarget;
        }

        public Context getContext() {
            return textViewBase.getContext();
        }
    }
}
