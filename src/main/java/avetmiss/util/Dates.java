package avetmiss.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}
