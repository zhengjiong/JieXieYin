package org.namofo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * @author zhengjiong
 * 2014-6-19 上午7:11:29
 */
public class UIHelper {
	static Toast toast;

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void toastMessage(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static void toastMessage(Context context, int msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

}
