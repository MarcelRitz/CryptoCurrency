package dev.cytronix.cryptocurrency.util;

import android.content.Intent;
import android.net.Uri;

public class AppStoreUtils {

    public static Intent getAppLink(String packageName) {
        Intent intent = getLink();
        intent.setData(Uri.parse("market://details?id=" + packageName));
        return intent;
    }

    public static Intent getBrowserLink(String packageName) {
        Intent intent = getLink();
        intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
        return intent;
    }

    private static Intent getLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
