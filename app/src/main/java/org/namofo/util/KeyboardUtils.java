package org.namofo.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * create by zhengjiong
 * Date: 2015-05-03
 * Time: 17:42
 */
public class KeyboardUtils {

    /**
     * 关闭键盘
     * @param context
     */
    public static void hideKeyBroad(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
