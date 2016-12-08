package avetmiss.domain;

import avetmiss.util.LabelValue;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.leftPad;

public class AvetmissUtil {

	/**  If a client commenced a qualification or a course:
	   - for the first time, the value must be 3
	   - in a previous collection year and has not completed the qualification, the value must be 4. **/
	public static String getCommencingCourseIdentifier(Date courseStart) {
		Calendar cal = new GregorianCalendar();
		int currentYear = cal.get(Calendar.YEAR);

		cal.setTime(courseStart);
		int startYear = cal.get(Calendar.YEAR);
		return (startYear < currentYear) ? "4" : "3";
	}
	
	// convert date to string in "DDMMYYYY" format
	private static SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyy");
	public static String toDate(Date date) {
        if(date == null) {
            return null;
        }

		return sf.format(date);
	}

    private static SimpleDateFormat sf1 = new SimpleDateFormat("yyyy");
	public static String getPurchasingContractYear(Date enrolmentStartDate) {
		return sf1.format(enrolmentStartDate);
	}

    public static List<LabelValue> asLabelValue(Map<String, String> valueToLabel) {
        return valueToLabel.entrySet()
				.stream()
				.map(entry -> LabelValue.labelValue(entry.getValue(), entry.getKey()))
				.collect(Collectors.toList());
    }

    public static String formattedRtoIdentifier(String TOID) {
        return leftPad(TOID, 10, '0');
    }

    public static String formattedRtoIdentifier(int TOID) {
        return formattedRtoIdentifier(String.valueOf(TOID));
    }

}