package avetmiss.util;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static avetmiss.util.StringUtil.isBlank;

public class Dates {

	public static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    public static int currentYear() {
        return new LocalDate().getYear();
    }

	public static Date today() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Returns <code>true</code> if the entity is effective at the check date:
	 * e.g: effectiveFromDate < checkDate < effectiveToDate.
	 * <p>
	 * Returns <code>true</code> if the effective date range is not specified
	 * (not applicable).
	 */
	public static boolean isDateInRange(Date from, Date to, Date checkDate) {
		if(from == null && to == null) {
			return true;
		}

		if (from != null
				&& !isSameDay(from, checkDate)
				&& from.after(checkDate)) {
			return false;
		}

        return !(to != null && !isSameDay(to, checkDate)
                && checkDate.after(to));
    }

	/**
     * <p>Checks if two date objects are on the same day ignoring time.</p>
     *
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     *
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @see org.apache.commons.lang.time.DateUtils
     */
    public static boolean isSameDay(Date date1, Date date2) {
       return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
    }

    public static boolean isDifferentDay(Date date1, Date date2) {
       return !org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
    }

    /**
     * A wrapper method of {@link org.apache.commons.lang.time.DateUtils#addDays(java.util.Date, int)}
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
    	return org.apache.commons.lang.time.DateUtils.addDays(date, amount);
    }

    /**
     * Return true if the given date string is a valid date input.
     * @see Constants#DATE_FORMAT
	 */
	public static boolean isDateInputValid(String dateStr) {
		return toDate(dateStr) != null;
	}

	/**
	 * Returns a Date object for the given date string. The date string will be
	 * parsed in the format defined in {@link Constants#DATE_FORMAT}.
	 * @param dateStr
	 * @return Date object representing the given string, returns
	 *         <code>null</code> if the string is invalid or empty.
	 */
	public static Date toDate(String dateStr) {
		if (isBlank(dateStr)) {
			return null;
		}
		try {
			return DEFAULT_DATE_FORMATTER.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

    /**
     * Returns a string representation of the given date in ISO8601 standard
     */
    public static String toISO(java.util.Date date) {
        LocalDate localDate = LocalDate.fromDateFields(date);
        return ISODateTimeFormat.date().print(localDate);
    }

 	public static Date toDateISO(String dateStr) {
		DateTimeFormatter fmt = ISODateTimeFormat.date();
		DateTime dt = fmt.parseDateTime(dateStr);
		return dt.toDate();
	}

  	private static final SimpleDateFormat TIME_FORMATTER_24HH_MM = new SimpleDateFormat("HH:mm");
	public static Date toTime(String time_HHmm) {
		if (isBlank(time_HHmm)) {
			return null;
		}
		
		try {
			return TIME_FORMATTER_24HH_MM.parse(time_HHmm);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String toTimeString(Date time) {
		if(time == null) {
			return "";
		}
		return TIME_FORMATTER_24HH_MM.format(time);
	}
	
	/**
	 * Returns a string representation of the given date object in the
	 * application-wide format. The date string will be formatted to the format
	 * defined in {@link Constants#DATE_FORMAT}.
	 * @param date
	 *            a date object
	 * @return a string representation of the date object in the
	 *         system-supported format. Returns an empty string if the date
	 *         object is null
	 */
	public static String toDateString(Date date) {
		if (date == null) {
			return "";
		}
		return DEFAULT_DATE_FORMATTER.format(date);
	}
	
	public static int getYear(Date date) {
		Validate.isTrue(date != null, "parameter 'date' can not be null");
		return date.getYear() + 1900;
	}

    public static int year(Date date) {
        return new LocalDate(date.getTime()).getYear();
    }

    public static Date date(int year, int month, int day) {
        return new LocalDate(year, month, day).toDateMidnight().toDate();
    }

    public static Date coming15thOfMonth() {
        LocalDate date = new LocalDate();
        if (date.getDayOfMonth() > 15) {
            date = date.plusMonths(1);
        }

        // set to 15th of the month
        return date.withDayOfMonth(15).toDateMidnight().toDate();
    }
}
