package com.qa.utils;

import com.qa.BaseTest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Test utility class common reusable method implementation.
 * @author Dineshkumar.
 *
 */

public class TestUtils extends BaseTest {

	public static final long WAIT = 10;

	/**
	 * Get the next future date from the current date based on number of days.
	 * @param daysToAdvance number of days.
	 * @return date
	 */
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

	/**
	 * Get the datetime format.
	 * @return date
	 */
	public String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
