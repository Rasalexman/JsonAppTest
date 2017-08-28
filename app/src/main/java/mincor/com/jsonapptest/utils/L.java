package mincor.com.jsonapptest.utils;

import mincor.com.jsonapptest.BuildConfig;

/**
 * Created by Alex on 05.11.2015.
 */

public class L {

    public static final String  DEFAULT_TAG = "-----> LOG",
                                ERROR_EXEPTION_TAG = "-----> ERROR";

    // Для простого примера однообразно и длинновато, принцип был бы ясен
    // на примере одного метода, зато этот код можно просто копипастнуть
    private L() {
    }

    // Здесь же поселим стектрейс
    public static void printStackTrace(Throwable t) {
        if (BuildConfig.DEBUG)
            t.printStackTrace();
    }

    /**
     * DEBUG OUTPUT WITH NO TAG PARAMETER
     * @param msg
     * @return
     */
    public static int d(String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.d(DEFAULT_TAG, msg);
    }

    /**
     * DEBUG OUTPUT WITH NO TAG PARAMETER
     * @param args
     * @return
     */
    public static int d(Object... args) {
        if (!BuildConfig.DEBUG)
            return 0;
        String msg = "";
        for (Object o : args){
            msg += o == null?"null ":(o.toString() + " ");
        }
        return android.util.Log.d(DEFAULT_TAG, msg);
    }

    public static int e(String msg){
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.e(ERROR_EXEPTION_TAG, msg);
    }

    /**
     * DEBUG OUTPUT WITH NO TAG PARAMETER
     * @param msg
     * @return
     */
    public static int i(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.i(tag, msg);
    }
}
