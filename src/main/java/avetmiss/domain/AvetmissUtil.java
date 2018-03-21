package avetmiss.domain;

import avetmiss.export.Client;
import avetmiss.export.Enrolment;
import avetmiss.util.LabelValue;

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

	public static String purchasingContractIdentifier(LocalDate courseStart, String TOID) {
		// The Purchasing Contract Identifier must be consistent with the
		// year the student commenced their course. That is, the Purchasing
		// Contract Identifier remains constant for a given student, course
		// and course commencement date.

		// The Purchasing Contract Identifier
		// should be left blank by ACE providers which are not paid by
		// Skills Victoria.

		// This field should be blank for all enrolments for which payment
		// is not being claimed through SVTS.
		String purchasingContractIdentifier = purchasingContractYear(courseStart) + TOID + "0";
		return purchasingContractIdentifier;
	}

	private static int purchasingContractYear(LocalDate enrolmentStartDate) {
		int year = enrolmentStartDate.getYear();

		if(year <= 2016) {
			// Course Commencement Date "10/10/2016" is earlier than the purchasing contract/service agreement
			// contract date "1/01/2017" that the RTO has with The Department.
			return 2017;
		}

		return year;
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

	public static List<EnrolmentSubject> collectCompetencies(List<Client> clients) {
		Set<EnrolmentSubject> competencies = new LinkedHashSet<>();
		for(Client client: clients) {
			List<EnrolmentSubject> subjects = client.enrolments().stream().map(Enrolment::getUnit).collect(Collectors.toList());
			competencies.addAll(subjects);
		}

		return new ArrayList<>(competencies);
	}
}