package ci6206.utils;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Random;

/**
 *Common methods
 *
 * Author Qiao Guo Jun
 * Date Sep 29, 2015
 * Version 1.0 
 */
public class Utils {
	public static Long generateUniqueId() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = MessageFormat.format("{0,number,#00}",
				cal.get(Calendar.MONTH) + 1);
		String day = MessageFormat.format("{0,number,#00}",
				cal.get(Calendar.DATE));
		String hour = MessageFormat.format("{0,number,#00}",
				cal.get(Calendar.HOUR_OF_DAY));
		String minute = MessageFormat.format("{0,number,#00}",
				cal.get(Calendar.MINUTE));
		String second = MessageFormat.format("{0,number,#00}",
				cal.get(Calendar.SECOND));
		String millSecond = MessageFormat.format("{0,number,#000}",
				cal.get(Calendar.MILLISECOND));
	
		Random random = new Random();
		String strNextInt = MessageFormat.format("{0,number,#00}",
				random.nextInt(90));
	
		return Long.parseLong(year + month + day + hour + minute + second + millSecond
				+ strNextInt);
	}
}
