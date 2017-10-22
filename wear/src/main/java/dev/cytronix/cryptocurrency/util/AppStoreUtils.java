package dev.cytronix.cryptocurrency.util;

import android.content.Intent;
import android.net.Uri;

public class AppStoreUtils {

    private static final String LINK_APP = "market://details?id=";
    private static final String LINK_WEB = "http://play.google.com/store/apps/details?id=";

    public static Intent getAppIntent(String packageName) {
        Intent intent = getIntent();
        intent.setData(Uri.parse(LINK_APP + packageName));
        return intent;
    }

    public static Intent getWebIntent(String packageName) {
        Intent intent = getIntent();
        intent.setData(Uri.parse(AppStoreUtils.getWebLink(packageName)));
        return intent;
    }

    private static Intent getIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static String getWebLink(String packageName) {
        return LINK_WEB + packageName;
    }
}
