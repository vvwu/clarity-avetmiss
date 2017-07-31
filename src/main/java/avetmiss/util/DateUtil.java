package avetmiss.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.isBlank;

public class DateUtil {

	public static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

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
				&& !org.apache.commons.lang.time.DateUtils.isSameDay(from, checkDate)
				&& from.after(checkDate)) {
			return false;
		}

        return !(to != null && !org.apache.commons.lang.time.DateUtils.isSameDay(to, checkDate)
                && checkDate.after(to));
    }

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

 	public static Date toDateISO(String dateIsoString) {
		if(isBlank(dateIsoString)) {
			return null;
		}

		LocalDate localDate = LocalDate.parse(dateIsoString, DateTimeFormatter.ISO_LOCAL_DATE);
		return Dates.toDate(localDate);
	}

    public static String toISO(Date date) {
		if (date == null) {
			return null;
		}
		return Dates.toLocalDate(date).format(DateTimeFormatter.ISO_DATE);
    }

    public static Date to0000(Date date) {
		return (date == null) ? null : Dates.toDate(Dates.toLocalDate(date));

	}

	public static String toDateString(Date date) {
		if (date == null) {
			return "";
		}
		return DEFAULT_DATE_FORMATTER.format(date);
	}

    public static int year(Date date) {
        return Dates.toLocalDate(date).getYear();
    }

    public static Date date(int year, int month, int day) {
		return toDate(LocalDate.of(year, month, day));
	}

    public static Date coming15thOfMonth() {
        LocalDate date = LocalDate.now();
        if (date.getDayOfMonth() > 15) {
            date = date.plusMonths(1);
        }

        // set to 15th of the month
        return toDate(date.withDayOfMonth(15));
    }

	public static LocalDate toLocalDate(Date date) {
		return (date == null) ? null : java.time.Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date toDate(LocalDate date) {
		return (date == null) ? null : new Date(getTime(date));
	}

	public static long getTime(LocalDate date) {
		return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}
