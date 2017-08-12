package dev.cytronix.cryptocurrency;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import dev.cytronix.cryptocurrency.util.FlavorUtils;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initAnalytics();
    }

    private void initAnalytics() {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        analytics.setAnalyticsCollectionEnabled(FlavorUtils.isProd());
    }
}
