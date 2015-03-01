package org.namofo.util;

import android.content.Context;
import android.os.Environment;

/**
 * 判斷sdcard是否存在
 * @Author: zhengjiong
 * Date: 2014-08-10
 * Time: 16:59
 */
public class SDCardUtils {

    public static boolean sdcardIsExist(Context context){
        if (android.os.Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}
