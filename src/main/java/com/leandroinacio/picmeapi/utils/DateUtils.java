package com.leandroinacio.picmeapi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	public final static String DEFAULT_FORMAT = "dd.MM.yyyy HH:mm:ss";
	
	public static String getDayMonthYear(Calendar calendar) {
		final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(calendar.getTime());
	}

	public static Integer getDayOfMonth(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Integer getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONTH);
	}

	public static Integer getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}
}
