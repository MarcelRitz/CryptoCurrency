package dev.cytronix.cryptocurrency.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsUtils {

    @SuppressWarnings("SameParameterValue")
    public static void trackEvent(Context context, String event, String itemId, String itemName, int quantity) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.QUANTITY, String.valueOf(quantity));

        FirebaseAnalytics.getInstance(context).logEvent(event, bundle);
    }
}
