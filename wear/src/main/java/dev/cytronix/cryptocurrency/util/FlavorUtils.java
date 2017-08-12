package dev.cytronix.cryptocurrency.util;

import dev.cytronix.cryptocurrency.BuildConfig;

public class FlavorUtils {

    public static boolean isDev() {
        return "dev".equals(BuildConfig.FLAVOR);
    }

    public static boolean isProd() {
        return "prod".equals(BuildConfig.FLAVOR);
    }
}
