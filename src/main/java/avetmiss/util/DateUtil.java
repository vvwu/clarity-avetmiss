package avetmiss.util;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

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

    public static String toISO(Date date) {
		if (date == null) {
			return null;
		}
		return Dates.toLocalDate(date).format(DateTimeFormatter.ISO_DATE);
    }

	public static String toDateString(LocalDate date) {
		if (date == null) {
			return "";
		}

		return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

    public static int year(Date date) {
        return Dates.toLocalDate(date).getYear();
    }

	public static long getTime(LocalDate date) {
		return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}
