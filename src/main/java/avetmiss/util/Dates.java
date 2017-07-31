package avetmiss.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Dates {

    public static int currentYear() {
        return LocalDate.now().getYear();
    }

    /**
     * Returns a string representation of the given date in ISO8601 standard
     */
    public static String toISO(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(DateTimeFormatter.ISO_DATE);
    }


	public static LocalDate toLocalDateISO(String dateStr) {
		if (dateStr == null) {
			return null;
		}

		return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
	}

    public static java.util.Date toDate(java.time.LocalDate date) {
        return (date == null) ? null : new java.util.Date(getTime(date));
    }

    public static java.util.Date toDate(java.time.LocalDateTime date) {
        return (date == null) ? null : new java.util.Date(getTime(date));
    }

    public static java.time.LocalDate toLocalDate(Date date) {
        return (date == null) ? null : java.time.Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static long getTime(java.time.LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long getTime(java.time.LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
