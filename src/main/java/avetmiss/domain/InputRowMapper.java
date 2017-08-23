package avetmiss.domain;

import avetmiss.util.CSVRowMapper;
import avetmiss.util.StringUtil;
import org.apache.commons.lang.Validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static avetmiss.util.Csv.*;
import static avetmiss.util.StringUtil.isBlank;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.hasLength;

public class InputRowMapper implements CSVRowMapper<EnrolmentInput> {

    private UnitRepository unitRepository;


    private List<String> errors;
    public InputRowMapper(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
        this.errors = newArrayList();
    }

    public List<String> errors() {
        return newArrayList(this.errors);
    }

    @Override
    public EnrolmentInput mapRow(String[] cols, int rowNum) {
        if (rowNum == 0) {
            // ignore the first header line
            return null;
        }

        // change from 0-based to 1-based index
        rowNum = rowNum + 1;

        try {
            EnrolmentInput enrolment = doMap(cols, rowNum);
            loadUnitDetails(enrolment);

            return enrolment;
        } catch (Exception e) {
            errors.add("rowNum=" + rowNum + ": " + e.getMessage());
            return null;
        }
    }

    private EnrolmentInput doMap(String[] cols, int rowNum) {
        //String hoursClaimedStr = cols[9];

        ensureRequiredColumns(cols);
        ensureColumnAContainsStudentID(cols);
        ensureColumnGContainsNominalHourStr(cols);
        ensureColumnJHoursAttendedContainsEmptyOrInteger(cols);

        ensureColumnKContainsValidDate(cols);
        ensureValidEndDateProvidedInColumnL(cols);
        ensureStartDateIsNoLaterThanEndDate(cols);

        EnrolmentInput enrolment = new EnrolmentInput();
        enrolment.setRowNum(rowNum);

        int studentID = Integer.parseInt(cols[COLUMN_A]);
        enrolment.setStudentId(studentID);

        enrolment.setStudentName(cols[COLUMN_B]);
        enrolment.setCourseCode(cols[COLUMN_C]);
        enrolment.setUnitCode(cols[COLUMN_E]);
        String nominalHourStr = cols[COLUMN_G];
        String supervisedHourStr = cols[COLUMN_H];
        String totalSupervisedHourStr = cols[COLUMN_I];
        String hoursAttendedStr = cols[COLUMN_J];

        String startDateStr = cols[COLUMN_K];
        String endDateStr = cols[COLUMN_L];
        String outcomeIdentifier = cols[COLUMN_M];
        String tuitionFee = cols[COLUMN_N];

        if(hasLength(totalSupervisedHourStr)) {
            enrolment.setTotalSupervisedHours(Integer.parseInt(totalSupervisedHourStr));
        }

        if (hasLength(hoursAttendedStr)) {
            enrolment.setHoursAttended(Integer.parseInt(hoursAttendedStr));
        }

        enrolment.setNominalHour(Integer.parseInt(nominalHourStr));

        enrolment.setStartDate(toLocalDate(startDateStr));
        enrolment.setEndDate(toLocalDate(endDateStr));
        enrolment.setOutcomeIdentifier(new OutcomeIdentifierNational(outcomeIdentifier));
        enrolment.setTuitionFee(tuitionFee);

        return enrolment;
    }

    private void loadUnitDetails(EnrolmentInput enrolment) {
        String unitCode = enrolment.getUnitCode();
        Unit unit = this.unitRepository.findByCode(unitCode);

        checkNotNull(unit, "rowNum=%s: unitCode '%s' not found in NTIS unit list", enrolment.getRowNum(), unitCode);

        enrolment.setUnit(
                new EnrolmentSubject(
                        unit.code(),
                        unit.name(),
                        unit.fieldOfEducationIdentifier(),
                        enrolment.nominalHour()));
    }

    private static LocalDate toLocalDate(String dateStr) {
        if (isBlank(dateStr)) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
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
        final int requiredColumns = COLUMN_N + 1;
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

    private void ensureColumnJHoursAttendedContainsEmptyOrInteger(String[] cols) {
        String hoursAttendedStr = cols[COLUMN_J];
        if(hasLength(hoursAttendedStr)) {
            Validate.isTrue(StringUtil.isInteger(hoursAttendedStr), "Must provide a valid 'hoursAttended' in [column I]");
        }
    }

    private void ensureColumnKContainsValidDate(String[] cols) {
        String startDateStr = cols[COLUMN_K];
        LocalDate startDate = toLocalDate(startDateStr);
        checkArgument(startDate != null, "Must provide a valid date in [column K] in format: dd/mm/yyyy");
    }

    private void ensureValidEndDateProvidedInColumnL(String[] cols) {
        String endDateStr = cols[COLUMN_L];
        LocalDate endDate = toLocalDate(endDateStr);
        checkArgument(endDate != null, "Must provide a valid date in [column L] in format: dd/mm/yyyy");
    }

    private void ensureStartDateIsNoLaterThanEndDate(String[] cols) {
        LocalDate startDate = toLocalDate(cols[COLUMN_K]);
        LocalDate endDate = toLocalDate(cols[COLUMN_L]);

        checkArgument(!startDate.isAfter(endDate), "Start date cannot be later than end date");
    }
}
