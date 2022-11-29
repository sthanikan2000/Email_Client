package EmailClient;

import java.text.*;
import java.util.Date;

public class DateValidator {
	// ------------------------------------------------------------------------------------------------
	private static final String dateFormat = "yyyy/MM/dd";
	private static final String timeFormat = "hh:mm:ss a";//Standard 12hrs clock format
	private static final DateFormat sdf = new SimpleDateFormat(dateFormat);
	private static final DateFormat stf = new SimpleDateFormat(timeFormat);

	// ------------------------------------------------------------------------------------------------
	public static boolean isValidDate(String date) {
		sdf.setLenient(false);
		// IF Lenient == true : sdf.parse(date) will only check for valid date format
		// ELSE IF Lenient ==false : sdf.parse(date) will check for valid date

		try {
			sdf.parse(date);
		} catch (ParseException e) {
			//Can't parse date. Therefore,Invalid Date
			return false;
		}
		return true;
	}

	// ------------------------------------------------------------------------------------------------
	public static String getToday() {
		// Return today's date as String in the format: yyyy/MM/dd
		return sdf.format(new Date()).toString();
	}
	// ------------------------------------------------------------------------------------------------
	public static String getTime() {
		// Return time right now as String in the timeformat
		return stf.format(new Date()).toString();
	}

	// ------------------------------------------------------------------------------------------------
	public static String getDateFormat() {
		return dateFormat;
	}
	// ------------------------------------------------------------------------------------------------
	public static boolean isSameDateMonthAsToday(String date) {
		String[] arr1 = date.strip().split("/");
		String[] arr2 = getToday().split("/");
		if (Integer.valueOf(arr1[2]) == Integer.valueOf(arr2[2])
				&& Integer.valueOf(arr1[1]) == Integer.valueOf(arr2[1])) {
			return true;
		}
		return false;
	}
	// ------------------------------------------------------------------------------------------------
	//The following method will return true, when date can be Birthday for BoD
	public static boolean isDateBoD(String BoD,String date) {
		String[] arr1 = BoD.split("/");
		String[] arr2 = date.strip().split("/");
		if (Integer.valueOf(arr1[2]) == Integer.valueOf(arr2[2])
				&& Integer.valueOf(arr1[1]) == Integer.valueOf(arr2[1])
				&& Integer.valueOf(arr1[0]) <= Integer.valueOf(arr2[0])) return true;
		return false;
	}

}
