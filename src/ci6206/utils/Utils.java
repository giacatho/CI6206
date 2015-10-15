package ci6206.utils;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Random;

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
	
	/**
	 * Validate if email address is a valid email.
	 * 
	 * @param email The email address to be validated.
	 * @return Return true if it is invalid email address, otherwise return false.
	 */
	public static boolean validateEmail(String email) {
		if (email != null) {
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check if a String is a numeric type.
	 * 
	 * @param str The String to be checked.
	 * @return Return true if a String is a positive numeric, otherwise return false.
	 */
	public static boolean isPositiveNumeric(String str)
	{
		if (str.matches("[-+]?\\d*\\.?\\d+")) {
			if (Double.parseDouble(str) > 0) {
				return true;
			}
		}
		
		return false;
	}
}
