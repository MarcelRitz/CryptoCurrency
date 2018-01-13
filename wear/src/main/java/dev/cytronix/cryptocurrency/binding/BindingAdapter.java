package dev.cytronix.cryptocurrency.binding;

import android.view.View;

public class BindingAdapter {

    @android.databinding.BindingAdapter("android:visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }
}
