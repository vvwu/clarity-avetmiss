package avetmiss.domain.nat;


import avetmiss.controller.payload.nat.EnrolmentFileRequest;
import avetmiss.domain.*;
import avetmiss.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.leftPad;

public class Nat00120EnrolmentFile {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public final static String DELIVERY_LOCATION_IDENTIFIER_QUEEN_STREET = "CITY";

    private final static Header header = Header(238,
            of("Training Organisation Delivery Location Identifier", 10),
            of("Client (Student) Identifier", 10),
            of("Subject (Module/Unit of Competency) Identifier", 12),
            of("Program (Qualification/Course) Identifier", 10),
            of("Activity Start Date (Enrolment Activity Start Date)", 8),
            of("Activity End Date (Enrolment Activity End Date)", 8),
            of("Delivery Mode Identifier", 2),
            of("Outcome Identifier - National", 2),
            of("Scheduled Hours", 4),
            of("Funding Source - National", 2),
            of("Commencing Program (Course) Identifier", 1),
            of("Training Contract Identifier - Australian Apprenticeships", 10),
            of("Client Identifier - Australian Apprenticeships", 10),
            of("Study Reason Identifier", 2),
            of("VET in Schools Flag", 1),
            of("Specific Funding (Specific Program) Identifier", 10),
            of("Outcome Identifier - Training Organisation", 3),
            of("Funding Source - State Training Authority", 3),
            of("Client Tuition Fee", 4),
            of("Fee Exemption/Concession Type Identifier", 1),
            of("Purchasing Contract Identifier", 12),
            of("Purchasing Contract Schedule Identifier", 3),
            of("Hours Attended", 4),
            of("Associated Course Identifier", 10),
            of("Program (Course) Commencement Date", 8),
            of("Eligibility Exemption indicator", 1),
            of("VET FEE-HELP (Income Contingent Loan) Indicator", 1),
            of("Industry code (ANZSIC)", 2),
            of("Enrolment Date", 8),
            of("Enrolment Identifier", 50),
            of("Client Fees - Other", 5),
            of("Delivery Provider ABN", 11),
            of("Funding Eligibility Key", 10));

    public String export(List<EnrolmentFileRequest> requests) {
        List<Row> rows = new ArrayList<>();
        for (EnrolmentFileRequest request: requests) {
            rows.add(exportOneRow(request));
        }
        return ExportHelper.writeToString(header, rows);
    }

    private Row exportOneRow(EnrolmentFileRequest request) {
        // data
        String deliveryLocationIdentifier = DELIVERY_LOCATION_IDENTIFIER_QUEEN_STREET;
        String unitIdentifier = request.unitCode;
        String courseIdentifier = request.courseIdentifier;
        String deliveryModeIdentifier = "10"; // 10 (Classroom based)

        // Outcome Identifier - National

        // Assessed
        // Value	Description
        // 20	Competency achieved/pass
        // 30	Competency not achieved/fail
        // 40	Withdrawn 51	Recognition of Prior Learning - granted
        // 52	Recognition of Prior Learning - not granted
        // 53	Recognition of Current Competency - granted 54	Recognition of Current Competency - not granted
        // 60	Credit transfer

        // Not yet assessed
        // Value	Description
        // 70	Continuing enrolment - Enrolment Activity End Date occurs in a future collection year.
        // 90	Result not yet available - Module is scheduled for completion in the current collection year.

        // Non-assessable
        // Value	Description
        // 81	Non-assessable enrolment - Satisfactorily completed 82	Non-assessable enrolment - Withdrawn or not satisfactorily completed

        LocalDate courseStart = Dates.toLocalDateISO(request.courseStartedDate);

        String scheduledHours = request.nominalHour + "";
        String commencingCourseIdentifier = AvetmissUtil.getCommencingCourseIdentifier(courseStart);

        String vetInSchoolsFlag = "N";
        String outcomeIdentifierTrainingOrganisation = "NYC"; //  (internal code for Not Yet Competent);
        String clientTuitionFee = leftPad(request.tuitionFee, 4, "0");

        String purchasingContractScheduleIdentifier = null;
        String courseCommencementDate = AvetmissUtil.toDate(courseStart);
        String eligibilityExemptionIndicator = "N";
        String VETFEEHELPIndicator = "N";

        String studentID = request.studentID;

        String fundingSourceStateIdentifier = request.fundingSourceStateIdentifier;
        String fundingSourceNationalIdentifier =
                fundingSourceNationalIdentifier(studentID, fundingSourceStateIdentifier);

        LocalDate enrolmentEndDate = Dates.toLocalDateISO(request.endDate);
        // Result not yet available (Code 90)
        OutcomeIdentifierNational outcomeIdentifierNational = new OutcomeIdentifierNational(request.outcomeIdentifier);
        int outcomeIdentifier = outcomeIdentifierNational.selfCorrectedCode(studentID, enrolmentEndDate);
        String anzsicCode = null;

        return new Row(
                deliveryLocationIdentifier,
                studentID,
                unitIdentifier,
                courseIdentifier,
                AvetmissUtil.toDate(Dates.toLocalDateISO(request.startDate)),
                AvetmissUtil.toDate(enrolmentEndDate),
                deliveryModeIdentifier,
                outcomeIdentifier + "",
                scheduledHours,
                fundingSourceNationalIdentifier,
                commencingCourseIdentifier,
                request.trainingContractIdentifierApprenticeships,
                request.clientIdentifierApprenticeships,
                request.studyReasonIdentifier,
                vetInSchoolsFlag,
                specificFundingIdentifier(),
                outcomeIdentifierTrainingOrganisation,
                fundingSourceStateIdentifier,
                clientTuitionFee,
                request.concessionTypeIdentifier,
                purchasingContractIdentifier(courseStart, request.rtoIdentifier),
                purchasingContractScheduleIdentifier,
                hoursAttended(request.hoursAttended),
                associatedCourseIdentifier(),
                courseCommencementDate,
                eligibilityExemptionIndicator,
                VETFEEHELPIndicator,
                anzsicCode,
                AvetmissUtil.toDate(request.enrolmentDate),
                request.enrolmentIdentifier,
                request.clientFeesOther,
                request.deliveryProviderABN,
                request.fundingEligibilityKey);
    }

    private String fundingSourceNationalIdentifier(String sid, String fundingSourceStateIdentifier) {
        try {
            return FundingSourceStateIdentifier.fundingSourceNationalIdentifierFrom
                    (fundingSourceStateIdentifier);
        } catch (Exception e) {
            throw new IllegalArgumentException("SID=" + sid + " " + e.getMessage());
        }
    }

    private String hoursAttended(String aHoursAttended) {
        if(isBlank(aHoursAttended)) {
            return "";
        }

        return leftPad(aHoursAttended, 4, "0");
    }

    private String associatedCourseIdentifier() {
        // This field will not be used by Victoria and RTO are advised to leave it blank
        return null;
    }

    private String specificFundingIdentifier() {
        // RTOs are not required to report his data element so no further details are included in these Victorian VET
        // Student Statistical Collection Guidelines
        // This field must be blank
        return null;
    }

    private String purchasingContractIdentifier(LocalDate courseStart, String TOID) {
        // The Purchasing Contract Identifier must be consistent with the
        // year the student commenced their course. That is, the Purchasing
        // Contract Identifier remains constant for a given student, course
        // and course commencement date.

        // The Purchasing Contract Identifier
        // should be left blank by ACE providers which are not paid by
        // Skills Victoria.

        // This field should be blank for all enrolments for which payment
        // is not being claimed through SVTS.
        String purchasingContractIdentifier = AvetmissUtil.getPurchasingContractYear(courseStart) + TOID + "0";
        return purchasingContractIdentifier;
    }


}
