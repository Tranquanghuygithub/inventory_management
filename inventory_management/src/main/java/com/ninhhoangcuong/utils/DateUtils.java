package com.ninhhoangcuong.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String datetoString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return simpleDateFormat.format(date);
	}
}
