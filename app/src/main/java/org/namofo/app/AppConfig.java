package org.namofo.app;

import android.content.Context;

import com.google.common.base.Strings;

import org.namofo.util.PreferencesUtils;

/**
 * create by zhengjiong
 * Date: 2015-03-07
 * Time: 11:32
 */
public class AppConfig {

    public static final String UID = "uid";

    /**
     * 根據是否存在uid,判斷用戶是否已經登錄
     * @param context
     */
    public static boolean isLogin(Context context) {
        String uid = PreferencesUtils.getString(context, UID, null);
        return !Strings.isNullOrEmpty(uid);
    }
}
