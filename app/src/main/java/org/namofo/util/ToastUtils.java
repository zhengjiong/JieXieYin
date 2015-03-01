package org.namofo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @Author: zhengjiong
 * Date: 2014-08-10
 * Time: 17:06
 */
public class ToastUtils {

    private static Toast toast = null;

    /**
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text){
        if (toast == null){
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else{
            toast.setText(text);
        }
        toast.show();
    }

    /**
     *
     * @param context
     * @param resId
     */
    public static void show(Context context, int resId){
        if (toast == null){
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }else{
            toast.setText(resId);
        }
        toast.show();
    }
}
