package org.namofo.util;

import android.util.Log;

import org.namofo.BuildConfig;

/**
 * create by zhengjiong
 * Date: 2015-05-07
 * Time: 22:15
 */
public class Debug {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
