package org.namofo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhengjiong 2014-5-20 上午6:45:46
 */
public class DBUtil {

	public static String dbName = "jiexieying.db";

	/**
	 * 判斷數據庫是否存在
	 * @param context
	 * @return
	 */
	public static boolean checkDataBase(Context context) {
		String packageName = context.getPackageName();
		String dbPath = "/data/data/" + packageName + "/databases/";
		
		SQLiteDatabase db = null;
		try {
			String dbFileName = dbPath + dbName;
            Log.i("zj", "dbFileName = " + dbFileName);
            db = SQLiteDatabase.openDatabase(dbFileName, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
            Log.i("zj", "數據庫不存在");
        }
        if (db != null) {
			db.close();
		}
		return db != null ? true : false;
	}
	/**
	 * 
	 * @param context
	 * @throws IOException
	 */
	public static void copyDataBase(Context context) throws IOException {
		if (!checkDataBase(context)) {
			String packageName = context.getPackageName();
			String dbPath = "/data/data/" + packageName + "/databases/";
			
			File dir = new File(dbPath);
			if (!dir.exists())
				dir.mkdir();
			
			String databaseFilenames = dbPath + dbName;
			
			FileOutputStream os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流
			InputStream is = context.getAssets().open(dbName);
			
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
				os.flush();
			}
			is.close();
			os.close();
		}
	}
}
