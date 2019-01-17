package daka.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import daka.enums.ResultEnum;
import daka.exception.MyException;

public class GetDateByString {
	public static Date GetDateByH5String(String dateString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String dateString = "2007-11-28 17:55:45";
		dateString = dateString.replace('T', ' ');
		dateString = dateString + ":00";
		System.out.println(dateString);
		Date date = null;
		try {
			date = df.parse(dateString);
//		System.out.println(df.format(date));
		} catch (Exception ex) {
			throw new MyException(ResultEnum.Date_FORMAT_WRONG);
		}
		return date;
	}
}
