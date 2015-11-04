package avetmiss.domain;

import avetmiss.util.Dates;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class InputRowMapperTest {

    private String[] mockHeader = {"mockheader"};

    private int studentId = 33642;
    private String studentName = "Nimesh Patel";
    private String courseName = "Certificate III in Hospitality (Patisserie) (SIT31107)";
    private String unitCode = "SITXCOM002A";
    private String unitName = "Work in a socially diverse environment";
    private int norminalHours = 20;
    private Integer hoursAttended = 10;
    private String startDate = "13/06/2013";
    private String endDate = "13/08/2013";
    private String outcomeIdentifier = "90";
    private String tuitionFee = "31";

    private String[] inputRow = {
            studentId + "",
            studentName,
            courseName,
            unitCode,
            unitName,
            norminalHours + "",
            hoursAttended + "",
            startDate,
            endDate,
            outcomeIdentifier,
            tuitionFee};

    private InputRowMapper inputRowMapper;

    @Before
    public void setup() {
        this.inputRowMapper = new InputRowMapper();
    }

    @Test
    public void shouldIgnoreTheFirstRowFromTheInputFileWhichIsTheHeader() {
        Enrolment enrolment = this.inputRowMapper.mapRow(mockHeader, 0);
        assertThat(enrolment, is(nullValue()));
    }

    @Test
    public void shouldMapAEnrolmentFromTheInputRow() {
        Enrolment enrolment = this.inputRowMapper.mapRow(inputRow, 1);

        assertThat(enrolment.getStudentId(), is(studentId));
        assertThat(enrolment.studentName(), is(studentName));
        assertThat(enrolment.courseName(), is(courseName));
        assertThat(enrolment.getUnitCode(), is(unitCode));
        assertThat(enrolment.nominalHour(), is(norminalHours));
        assertThat(enrolment.hoursAttended(), is(hoursAttended));
        assertThat(enrolment.startDate(), is(Dates.toDate(startDate)));
        assertThat(enrolment.endDate(), is(Dates.toDate(endDate)));
        assertThat(enrolment.getOutcomeIdentifier().code() + "", is(outcomeIdentifier));
        assertThat(enrolment.tuitionFee(), is(tuitionFee));
    }

    @Test
    public void shouldLogErrorsNominalHoursIsUnparsable() {
        String[] inputRow = {
                studentId + "",
                studentName,
                courseName,
                unitCode,
                unitName,
                "unparsable-norminal-hours",
                hoursAttended + "",
                startDate,
                endDate,
                outcomeIdentifier,
                tuitionFee};

        this.inputRowMapper.mapRow(inputRow, 1);
        assertThat(inputRowMapper.errors().contains("rowNum=2: Must provide a valid 'nominalHour' in [column F]"), is(true));
    }

    @Test
    public void shouldLogErrorsHoursAttendedIsUnparsable() {
        String[] inputRow = {
                studentId + "",
                studentName,
                courseName,
                unitCode,
                unitName,
                norminalHours + "",
                "unparsable-hours-attended",
                startDate,
                endDate,
                outcomeIdentifier,
                tuitionFee};

        this.inputRowMapper.mapRow(inputRow, 1);
        assertThat(inputRowMapper.errors().contains("rowNum=2: Must provide a valid 'hoursAttended' in [column G]"), is(true));
    }

    @Test
    public void shouldLogErrorsIfStartDatesAreUnparsable() {
        String[] inputRow = {
                studentId + "",
                studentName,
                courseName,
                unitCode,
                unitName,
                norminalHours + "",
                hoursAttended + "",
                "unparsable-from",
                endDate,
                outcomeIdentifier,
                tuitionFee};

        this.inputRowMapper.mapRow(inputRow, 1);
        assertThat(inputRowMapper.errors().contains("rowNum=2: Must provide a valid date in [column H] in format: dd/mm/yyyy"), is(true));
    }

    @Test
    public void shouldLogErrorsIfEndDatesAreUnparsable() {
        String[] inputRow = {
                studentId + "",
                studentName,
                courseName,
                unitCode,
                unitName,
                norminalHours + "",
                hoursAttended + "",
                startDate,
                "unparsable-from",
                outcomeIdentifier,
                tuitionFee};

        this.inputRowMapper.mapRow(inputRow, 1);
        assertThat(inputRowMapper.errors().contains("rowNum=2: Must provide a valid date in [column I] in format: dd/mm/yyyy"), is(true));
    }


    @Test
    public void shouldLogErrorsIfStartDateIsAfterEndDate() {
        String[] inputRow = {
                studentId + "",
                studentName,
                courseName,
                unitCode,
                unitName,
                norminalHours + "",
                hoursAttended + "",
                endDate,
                startDate,
                outcomeIdentifier,
                tuitionFee};

        Enrolment enrolment = this.inputRowMapper.mapRow(inputRow, 1);
        assertThat(inputRowMapper.errors().contains("rowNum=2: Start date cannot be later than end date"), is(true));

    }

}