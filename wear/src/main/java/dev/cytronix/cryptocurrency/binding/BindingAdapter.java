package dev.cytronix.cryptocurrency.binding;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import dev.cytronix.cryptocurrency.util.PriceUtils;
import dev.cytronix.data.cryptowat.model.Price;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("android:visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @androidx.databinding.BindingAdapter({"android:price", "android:currency"})
    public static void setPrice(TextView textView, double price, String currency) {
        Log.v("CyTronix", "t0="+price+","+currency);
        textView.setText((Price.PRICE_DEFAULT == price) ? "-" : PriceUtils.format(price) + currency);
    }
}
