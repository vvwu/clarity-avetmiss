package avetmiss.domain;

import avetmiss.util.CSVRowMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static avetmiss.util.Csv.*;
import static avetmiss.util.StringUtil.isBlank;
import static avetmiss.util.StringUtil.isInteger;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.hasLength;

public class InputRowMapper implements CSVRowMapper<EnrolmentInput> {

    enum column {
        SID("SID", COLUMN_A, "column A"),
        COURSE_IDENTIFIER("Course Identifier", COLUMN_B, "column B"),
        UNIT_CODE("Unit Code", COLUMN_C, "column C"),
        NOMINAL_HOURS("Nominal Hours", COLUMN_D, "column D"),
        TOTAL_SUPERVISED_HOURS("Total Supervised Hours", COLUMN_E, "column E"),
        HOURS_ATTENDED("Hours Attended", COLUMN_F, "column F"),
        START_DATE("Start Date", COLUMN_G, "column G"),
        END_DATE("End Date", COLUMN_H, "column H"),
        OUTCOME_IDENTIFIER("Outcome Identifier", COLUMN_I, "column I"),
        TUITION_FEE("Tuition Fee", COLUMN_J, "column J"),
        CLIENT_OTHER_FEE("Client Other Fee", COLUMN_K, "column K");

        private String title;
        private int index;
        private String indexString;

        column(String title, int index, String indexString) {
            this.title = title;
            this.index = index;
            this.indexString = indexString;
        }

        public static Collection<String> requiredColumnNames() {
            return Arrays.stream(column.values()).map(column -> column.title).collect(Collectors.toList());
        }
    }


    private UnitRepository unitRepository;


    private List<String> errors;
    public InputRowMapper(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
        this.errors = new ArrayList<>();
    }

    public List<String> errors() {
        return new ArrayList<>(this.errors);
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
        ensureRequiredColumns(cols);
        ensureColumnAContainsStudentID(cols);
        ensureColumnDContainsNominalHourStr(cols);
        ensureColumnFHoursAttendedContainsEmptyOrInteger(cols);

        ensureColumnGContainsValidDate(cols);
        ensureValidEndDateProvidedInColumnH(cols);
        ensureStartDateIsNoLaterThanEndDate(cols);
        ensureColumnKClientOtherFeeContainsValidInteger(cols);

        EnrolmentInput enrolment = new EnrolmentInput();
        enrolment.setRowNum(rowNum);

        int studentID = Integer.parseInt(cols[column.SID.index]);
        enrolment.setStudentId(studentID);

        enrolment.setCourseCode(cols[column.COURSE_IDENTIFIER.index]);
        enrolment.setUnitCode(cols[column.UNIT_CODE.index]);
        String nominalHourStr = cols[column.NOMINAL_HOURS.index];
        String totalSupervisedHourStr = cols[column.TOTAL_SUPERVISED_HOURS.index];
        String hoursAttendedStr = cols[column.HOURS_ATTENDED.index];

        String startDateStr = cols[column.START_DATE.index];
        String endDateStr = cols[column.END_DATE.index];
        String outcomeIdentifier = cols[column.OUTCOME_IDENTIFIER.index];
        String tuitionFee = cols[column.TUITION_FEE.index];
        String clientOtherFee = cols[column.CLIENT_OTHER_FEE.index];

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
        enrolment.setClientOtherFee(Integer.parseInt(clientOtherFee));

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

    private void ensureRequiredColumns(String[] cols) {
        final int requiredColumns = column.values().length;
        checkArgument(cols.length == requiredColumns, "%s columns are provided, but %s columns are required, expected columns: %s",
                cols.length, requiredColumns, column.requiredColumnNames());
    }

    private void ensureColumnAContainsStudentID(String[] cols) {
        column sidColumn = column.SID;

        String sid = cols[sidColumn.index];
        checkArgument(isInteger(sid), "Must provide a valid Student ID in [%s]", sidColumn.index);
    }

    private void ensureColumnKClientOtherFeeContainsValidInteger(String[] cols) {
        column col = column.CLIENT_OTHER_FEE;

        String clientOtherFee = cols[col.index];
        checkArgument(isInteger(clientOtherFee), "Must provide a valid integer in column [%s] 'ClientOtherFee'", col.index);
    }

    private void ensureColumnDContainsNominalHourStr(String[] cols) {
        column nominalHours = column.NOMINAL_HOURS;

        String nominalHourStr = cols[nominalHours.index];
        checkArgument(isInteger(nominalHourStr), "Must provide a valid 'nominalHour' in [%s]", nominalHours.indexString);
    }

    private void ensureColumnFHoursAttendedContainsEmptyOrInteger(String[] cols) {
        column hoursAttended = column.HOURS_ATTENDED;

        String hoursAttendedStr = cols[hoursAttended.index];
        if(hasLength(hoursAttendedStr)) {
            checkArgument(isInteger(hoursAttendedStr), "Must provide a valid 'hoursAttended' in [%s]", hoursAttended.indexString);
        }
    }

    private void ensureColumnGContainsValidDate(String[] cols) {
        column startDateColumn = column.START_DATE;

        String startDateStr = cols[startDateColumn.index];
        LocalDate startDate = toLocalDate(startDateStr);
        checkArgument(startDate != null, "Must provide a valid date in [%s] in format: dd/mm/yyyy", startDateColumn.indexString);
    }

    private void ensureValidEndDateProvidedInColumnH(String[] cols) {
        column endDateColumn = column.END_DATE;

        String endDateStr = cols[endDateColumn.index];
        LocalDate endDate = toLocalDate(endDateStr);
        checkArgument(endDate != null, "Must provide a valid date in [%s] in format: dd/mm/yyyy", endDateColumn.indexString);
    }

    private void ensureStartDateIsNoLaterThanEndDate(String[] cols) {
        LocalDate startDate = toLocalDate(cols[column.START_DATE.index]);
        LocalDate endDate = toLocalDate(cols[column.END_DATE.index]);

        checkArgument(!startDate.isAfter(endDate), "Start date cannot be later than end date");
    }
}
