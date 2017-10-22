package dev.cytronix.cryptocurrency.util;

import android.content.Context;
import android.content.Intent;

public class IntentUtils {

    public static boolean isAvailable(Context context, Intent intent) {
        return (intent.resolveActivity(context.getPackageManager()) != null);
    }
}
