package dev.cytronix.cryptocurrency.util;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class FabricUtils {

    @SuppressWarnings("SameParameterValue")
    public static void trackEvent(String event, String name, float value) {
        Answers.getInstance().logCustom(new CustomEvent(event)
                .putCustomAttribute(name, value));
    }

    @SuppressWarnings("SameParameterValue")
    public static void trackEvent(String event, String name, String value) {
        Answers.getInstance().logCustom(new CustomEvent(event)
                .putCustomAttribute(name, value));
    }
}
