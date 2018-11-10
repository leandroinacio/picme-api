package com.leandroinacio.picmeapi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	public final static String DEFAULT_FORMAT = "dd.MM.yyyy HH:mm:ss.SSSZ";
	
	public static String getDayMonthYear(Calendar calendar) {
		final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(calendar).toString();
	}
}
