package dev.cytronix.cryptocurrency.binding;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import dev.cytronix.cryptocurrency.util.PriceUtils;

public class BindingAdapter {

    @android.databinding.BindingAdapter("android:visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @android.databinding.BindingAdapter({"app:price", "app:currency"})
    public static void setPrice(TextView textView, double price, String currency) {
        textView.setText(PriceUtils.format(price) + currency);
    }
}
