package avetmiss.domain;

import avetmiss.export.Client;
import avetmiss.export.Enrolment;
import avetmiss.util.LabelValue;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.leftPad;

public class AvetmissUtil {

	/**  If a client commenced a qualification or a course:
	   - for the first time, the value must be 3
	   - in a previous collection year and has not completed the qualification, the value must be 4. **/
	public static String getCommencingCourseIdentifier(LocalDate courseStart) {
		int currentYear = LocalDate.now().getYear();
		int startYear = courseStart.getYear();
		return (startYear < currentYear) ? "4" : "3";
	}
	
	// convert date to string in "DDMMYYYY" format
	public static String toDate(LocalDate date) {
        if(date == null) {
            return null;
        }

		return DateTimeFormatter.ofPattern("ddMMyyyy").format(date);
	}

	public static String getPurchasingContractYear(LocalDate enrolmentStartDate) {
		return DateTimeFormatter.ofPattern("yyyy").format(enrolmentStartDate);
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


	// convert date to string in "DDMMYYYY" format
	private static SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyy");
	public static String toDate(Date date) {
		return sf.format(date);
	}

	private static SimpleDateFormat sf1 = new SimpleDateFormat("yyyy");
	public static String getPurchasingContractYear(Date enrolmentStartDate) {
		return sf1.format(enrolmentStartDate);
	}

	public static Set<EnrolmentSubject> collectCompetencies(List<Client> clients) {
		Set<EnrolmentSubject> competencies = new LinkedHashSet<>();
		for(Client client: clients) {
			List<EnrolmentSubject> subjects = client.enrolments().stream().map(Enrolment::getUnit).collect(Collectors.toList());
			competencies.addAll(subjects);
		}
		return competencies;
	}
}