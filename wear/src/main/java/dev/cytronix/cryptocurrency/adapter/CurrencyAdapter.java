package dev.cytronix.cryptocurrency.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.cryptocurrency.databinding.ItemCurrencyBinding;
import dev.cytronix.data.cryptowat.model.Price;

public class CurrencyAdapter extends WearableRecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<Price> prices;

    public CurrencyAdapter(List<Price> prices) {
        this.prices = prices;
    }

    @NonNull
    @Override
    public CurrencyAdapter.CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCurrencyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_currency, parent, false);
        return new CurrencyAdapter.CurrencyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Price price = prices.get(position);
        holder.bind(price);
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class CurrencyViewHolder extends WearableRecyclerView.ViewHolder {

        private ItemCurrencyBinding binding;

        public CurrencyViewHolder(ItemCurrencyBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(Price price) {
            binding.setPrice(price);
        }
    }
}
