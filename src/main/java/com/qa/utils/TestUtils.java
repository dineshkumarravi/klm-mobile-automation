package com.qa.utils;

import com.qa.BaseTest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestUtils extends BaseTest {
	public static final long WAIT = 10;

	public static int getNextDate(int daysToAdvance){
		int date = 0;
		try{
			Calendar cal = Calendar.getInstance();
			Date cDate = cal.getTime();
			System.out.println(cal.get(Calendar.DAY_OF_MONTH));
			cal.add(Calendar.DATE, daysToAdvance);
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
			date = cal.get(Calendar.DAY_OF_MONTH);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
