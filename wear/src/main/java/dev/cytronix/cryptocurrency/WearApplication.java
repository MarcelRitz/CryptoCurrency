package dev.cytronix.cryptocurrency;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.analytics.FirebaseAnalytics;

import dev.cytronix.cryptocurrency.util.FlavorUtils;
import dev.cytronix.data.DataApplication;
import io.fabric.sdk.android.Fabric;

public class WearApplication extends DataApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initAnalytics();
        initCrashlytics();
    }

    private void initAnalytics() {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        analytics.setAnalyticsCollectionEnabled(FlavorUtils.isProd());
    }

    private void initCrashlytics() {
        Crashlytics crashlytics = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlytics);
    }
}
