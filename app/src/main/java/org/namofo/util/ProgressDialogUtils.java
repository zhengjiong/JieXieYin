package org.namofo.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 *
 * @Author: zhengjiong
 * Date: 2014-08-10
 * Time: 17:30
 */
public class ProgressDialogUtils {
    private static ProgressDialog mProgressDialog;

    public static void show(Context context, String message){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(message);

        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        mProgressDialog.show();
    }

    public static void hide(){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
