package org.namofo.util;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.integer;

/**
 * 日期工具类
 * @author zhengjiong
 * 2014-6-19 上午7:11:18
 */
public class DateTools {

	private static final long SECOND 	= 1000;
	private static final long MINUTE 	= SECOND * 60;
	private static final long HOUR 	= MINUTE * 60;
	private static final long DAY 		= HOUR * 24;
	
	
	/**
	 * 获取传入时间后天的TimeMillis
	 * @param pTimeMillis
	 * @return
	 */
	public static long get2DaysLaterTime(long pTimeMillis){
		Date date = new Date(pTimeMillis);
		
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		
		return date.getTime() + 2 * DAY;
	}
	
	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串。
	 * @param pDate 指定的日期
	 * @param format 日期格式化字符串
	 * @return 指定日期的格式化字符串
	 */
	public static String getFormatDateTime(Date pDate, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(pDate);
	}
	
	/**
	 * 根据时间戳获取日期(yyyy-MM-dd)
	 * @param pTimeMillis
	 * @return
	 */
	public static String getFormatDate(long pTimeMillis){
		Date date = new Date(pTimeMillis);
		return getFormatDateTime(date, "yyyy-MM-dd");
	}
	
	/**
	 * 根据时间戳获取日期(yyyy-MM-dd)
	 * @param pTimeMillis
	 * @return
	 */
	public static String getFormatDate(long pTimeMillis, String format){
		Date date = new Date(pTimeMillis);
		return getFormatDateTime(date, format);
	}
	
	/**
	 * 获取当前日期字符串 (格式：yyyy-MM-dd)
	 * @return 
	 */
	public static String getCurrentDate() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取当前日期时间字符串 (格式为yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 查询剩余的天数
	 * @param endTimeMillis 最后时间
	 * @return 剩下的天数(相对当前的时间)
	 */
	public static int getRemainingDays(long endTimeMillis){
		long currentTimeMillis = System.currentTimeMillis();
		long x = endTimeMillis - currentTimeMillis;
		if (x > DAY) {
			int dayNum = (int)(x / DAY);
			
			return dayNum;
		}
		return 0;
	}
}

