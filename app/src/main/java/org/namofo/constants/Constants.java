package org.namofo.constants;

import android.os.Environment;

import java.io.File;

/**
 * 
 * @author zhengjiong
 * 2014-7-5 下午2:43:23
 */
public class Constants {
	public static final String DB_NAME = "jiexieying.db";
    public static final String PDF_SAVE_PATH = Environment.getExternalStorageDirectory()
            + File.separator +"JieXieYin" + File.separator + "pdf" +File.separator;
    public static final String HOST_URL = "http://app.namofo.org/app.do";
}
