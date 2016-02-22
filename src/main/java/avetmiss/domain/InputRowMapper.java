package avetmiss.domain;

import avetmiss.util.CSVRowMapper;
import avetmiss.util.StringUtil;
import org.apache.commons.lang.Validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static avetmiss.util.Csv.*;
import static avetmiss.util.StringUtil.isBlank;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.hasLength;

public class InputRowMapper implements CSVRowMapper<Enrolment> {

    private List<String> errors;
    public InputRowMapper() {
        this.errors = newArrayList();
    }

    public List<String> errors() {
        return newArrayList(this.errors);
    }

    @Override
    public Enrolment mapRow(String[] cols, int rowNum) {
        if (rowNum == 0) {
            // ignore the first header line
            return null;
        }

        // change from 0-based to 1-based index
        rowNum = rowNum + 1;

        try {
            return doMap(cols, rowNum);
        } catch (Exception e) {
            errors.add("rowNum=" + rowNum + ": " + e.getMessage());
            return null;
        }
    }

    private Enrolment doMap(String[] cols, int rowNum) {
        //String hoursClaimedStr = cols[9];

        ensureRequiredColumns(cols);
        ensureColumnAContainsStudentID(cols);
        ensureColumnGContainsNominalHourStr(cols);
        ensureHoursAttendedColumnContainsEmptyOrInteger(cols);

        ensureColumnJContainsValidDate(cols);
        ensureValidEndDateProvided(cols);
        ensureStartDateIsNoLaterThanEndDate(cols);

        Enrolment enrolment = new Enrolment();
        enrolment.setRowNum(rowNum);

        int studentID = Integer.parseInt(cols[COLUMN_A]);
        enrolment.setStudentId(studentID);

        enrolment.setStudentName(cols[COLUMN_B]);
        enrolment.setCourseCode(cols[COLUMN_C]);
        enrolment.setUnitCode(cols[COLUMN_E]);
        String nominalHourStr = cols[COLUMN_G];
        String supervisedHourStr = cols[COLUMN_H];
        String hoursAttendedStr = cols[COLUMN_I];

        String startDateStr = cols[COLUMN_J];
        String endDateStr = cols[COLUMN_K];
        String outcomeIdentifier = cols[COLUMN_L];
        String tuitionFee = cols[COLUMN_M];

        if(hasLength(supervisedHourStr)) {
            enrolment.setSupervisedHours(Integer.parseInt(supervisedHourStr));
        }

        if (hasLength(hoursAttendedStr)) {
            enrolment.setHoursAttended(Integer.parseInt(hoursAttendedStr));
        }

                enrolment.setNominalHour(Integer.parseInt(nominalHourStr));

        enrolment.setStartDate(toDate(startDateStr));
        enrolment.setEndDate(toDate(endDateStr));
        enrolment.setOutcomeIdentifier(new OutcomeIdentifierNational(outcomeIdentifier));
        enrolment.setTuitionFee(tuitionFee);
        return enrolment;
    }

    private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yy");
    private static Date toDate(String dateStr) {
        if (isBlank(dateStr)) {
            return null;
        }
        try {
            return DEFAULT_DATE_FORMATTER.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private Collection<String> requiredColumnNames() {
        return Collections.unmodifiableCollection(
                asList("SID",
                        "Student Name",
                        "Course Identifier",
                        "Course Name",
                        "Unit Code",
                        "Unit Name",
                        "Nominal Hours",
                        "Supervised Hours",
                        "Hours Attended",
                        "Start Date",
                        "End Date",
                        "Outcome Identifier"));
    }

    private void ensureRequiredColumns(String[] cols) {
        final int requiredColumns = COLUMN_M + 1;
        checkArgument(cols.length == requiredColumns, "%s columns are provided, but %s columns are required, expected columns: %s",
                cols.length, requiredColumns, requiredColumnNames());
    }

    private void ensureColumnAContainsStudentID(String[] cols) {
        String sid = cols[COLUMN_A];
        checkArgument(StringUtil.isInteger(sid), "Must provide a valid Student ID in [column A]");
    }

    private void ensureColumnGContainsNominalHourStr(String[] cols) {
        String nominalHourStr = cols[COLUMN_G];
        checkArgument(StringUtil.isInteger(nominalHourStr), "Must provide a valid 'nominalHour' in [column G]");
    }

    private void ensureHoursAttendedColumnContainsEmptyOrInteger(String[] cols) {
        String hoursAttendedStr = cols[COLUMN_I];
        if(hasLength(hoursAttendedStr)) {
            Validate.isTrue(StringUtil.isInteger(hoursAttendedStr), "Must provide a valid 'hoursAttended' in [column I]");
        }
    }

    private void ensureColumnJContainsValidDate(String[] cols) {
        String startDateStr = cols[COLUMN_J];
        Date startDate = toDate(startDateStr);
        checkArgument(startDate != null, "Must provide a valid date in [column J] in format: dd/mm/yyyy");
    }

    private void ensureValidEndDateProvided(String[] cols) {
        String endDateStr = cols[COLUMN_K];
        Date endDate = toDate(endDateStr);
        checkArgument(endDate != null, "Must provide a valid date in [column K] in format: dd/mm/yyyy");
    }

    private void ensureStartDateIsNoLaterThanEndDate(String[] cols) {
        Date startDate = toDate(cols[COLUMN_J]);
        Date endDate = toDate(cols[COLUMN_K]);

        checkArgument(!startDate.after(endDate), "Start date cannot be later than end date");
    }
}
