package avetmiss.domain;

import avetmiss.util.CSVRowMapper;
import avetmiss.util.StringUtil;
import org.apache.commons.lang.Validate;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static avetmiss.util.Csv.*;
import static avetmiss.util.StringUtil.isBlank;
import static com.google.common.collect.Lists.newArrayList;

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
        ensureColumnFContainsNominalHourStr(cols);
        ensureHoursAttendedColumnContainsEmptyOrInteger(cols);

        ensureColumnHContainsValidDate(cols);
        ensureColumnIContainsValidDate(cols);
        ensureStartDateIsNoLaterThanEndDate(cols);

        Enrolment enrolment = new Enrolment();
        enrolment.setRowNum(rowNum);

        int studentID = Integer.parseInt(cols[COLUMN_A]);
        enrolment.setStudentId(studentID);

        enrolment.setStudentName(cols[COLUMN_B]);
        enrolment.setCourseName(cols[COLUMN_C]);
        enrolment.setUnitCode(cols[COLUMN_D]);
        // enrolment.setUnitname(cols[COLUMN_E]);

        String nominalHourStr = cols[COLUMN_F];
        String hoursAttendedStr = cols[COLUMN_G];

        String startDateStr = cols[COLUMN_H];
        String endDateStr = cols[COLUMN_I];
        String outcomeIdentifier = cols[COLUMN_J];
        String tuitionFee = cols[COLUMN_K];

        if (StringUtils.hasLength(hoursAttendedStr)) {
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
                Arrays.asList(
                        "SID",
                        "Student Name",
                        "Course Name",
                        "Unit Code",
                        "Unit Name",
                        "Nominal Hours",
                        "Hours Attended",
                        "Start Date",
                        "End Date",
                        "Outcome Identifier"));
    }

    private void ensureRequiredColumns(String[] cols) {
        final int REQUIRED_COLUMNS = COLUMN_K + 1;
        Validate.isTrue(cols.length == REQUIRED_COLUMNS, cols.length + " columns are provided, but " + REQUIRED_COLUMNS + " columns are required: " + requiredColumnNames());
    }

    private void ensureColumnAContainsStudentID(String[] cols) {
        String sid = cols[COLUMN_A];
        Validate.isTrue(StringUtil.isInteger(sid), "Must provide a valid Student ID in [column A]");
    }

    private void ensureColumnFContainsNominalHourStr(String[] cols) {
        String nominalHourStr = cols[COLUMN_F];
        Validate.isTrue(StringUtil.isInteger(nominalHourStr), "Must provide a valid 'nominalHour' in [column F]");
    }

    private void ensureHoursAttendedColumnContainsEmptyOrInteger(String[] cols) {
        String hoursAttendedStr = cols[COLUMN_G];
        if(StringUtils.hasLength(hoursAttendedStr)) {
            Validate.isTrue(StringUtil.isInteger(hoursAttendedStr), "Must provide a valid 'hoursAttended' in [column G]");
        }
    }

    private void ensureColumnHContainsValidDate(String[] cols) {
        String startDateStr = cols[COLUMN_H];
        Date startDate = toDate(startDateStr);
        Validate.notNull(startDate, "Must provide a valid date in [column H] in format: dd/mm/yyyy");
    }

    private void ensureColumnIContainsValidDate(String[] cols) {
        String endDateStr = cols[COLUMN_I];
        Date endDate = toDate(endDateStr);
        Validate.notNull(endDate, "Must provide a valid date in [column I] in format: dd/mm/yyyy");
    }

    private void ensureStartDateIsNoLaterThanEndDate(String[] cols) {
        Date startDate = toDate(cols[COLUMN_H]);
        Date endDate = toDate(cols[COLUMN_I]);

        Validate.isTrue(!startDate.after(endDate), "Start date cannot be later than end date");
    }


}
