package com.training.OnlineTraining.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateUtils {

	public static Date getDateFromString(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = formatter.parse(dateString);
		return new Date(utilDate.getTime());
	}

	public static String getStringFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static Date getTodayDate() {
		long currentTimeMillis = System.currentTimeMillis();
		return new Date(currentTimeMillis);
	}
}
