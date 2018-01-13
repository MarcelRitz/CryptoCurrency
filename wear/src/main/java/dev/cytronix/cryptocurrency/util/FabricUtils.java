package dev.cytronix.cryptocurrency.util;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class FabricUtils {

    public static final String EVENT_COMPLICATION = "Complication";
    public static final String EVENT_MENU = "Menu";
    public static final String NAME_ACTIVATED = "Activated";
    public static final String NAME_DEACTIVATED = "Deactivated";
    public static final String MENU_REFRESH = "Menu Refresh";
    public static final String MENU_DONATION = "Menu Donation";
    public static final String MENU_SHARE = "Menu Share";
    public static final String MENU_RATING = "Menu Rating";
    public static final String MENU_SETTINGS = "Menu Settings";

    @SuppressWarnings("SameParameterValue")
    public static void trackEvent(String event, String name, float value) {
        Answers.getInstance().logCustom(new CustomEvent(event)
                .putCustomAttribute(name, value));
    }

    public static void trackEvent(String event, String name, String value) {
        Answers.getInstance().logCustom(new CustomEvent(event)
                .putCustomAttribute(name, value));
    }
}
