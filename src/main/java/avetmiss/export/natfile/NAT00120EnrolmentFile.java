package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.EnrolmentFileRequest;
import avetmiss.domain.OutcomeIdentifierNational;
import avetmiss.domain.ProgramEnrolmentIdentifier;
import avetmiss.domain.PurchasingContractIdentifier;
import avetmiss.export.Client;
import avetmiss.export.Enrolment;
import avetmiss.util.DateUtil;
import avetmiss.util.Dates;
import avetmiss.util.hudson.TaskListener;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static avetmiss.util.DateUtil.toISO;
import static avetmiss.util.Dates.toISO;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.leftPad;
import static org.springframework.util.StringUtils.hasLength;

public class NAT00120EnrolmentFile {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String deliveryProviderABN;
    public NAT00120EnrolmentFile(String deliveryProviderABN) {
        this.deliveryProviderABN = deliveryProviderABN;
    }

    public List<EnrolmentFileRequest> enrolmentFileRequests(List<Client> clients, String TOID, TaskListener out) {
        List<EnrolmentFileRequest> rows = new ArrayList<>();

        int totalCount = 0;
        for (Client client : clients) {
            for (Enrolment enrolment : client.enrolments()) {
                try {
                    totalCount++;

                    EnrolmentFileRequest enrolmentFileRequest = oneEnrolmentFileRequestRow(client, enrolment, TOID);

                    rows.add(enrolmentFileRequest);
                } catch (Exception e) {
                    String error = format("NAT00120EnrolmentFile failed at enrolment, rowNum: %s, error: %s", enrolment.getRowNum(), e.getMessage());
                    out.error(error);
                }
            }
        }

        out.info("Export %s enrolments", totalCount);
        return rows;
    }


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
    private EnrolmentFileRequest oneEnrolmentFileRequestRow(Client client, Enrolment enrolment, String TOID) {
        EnrolmentFileRequest request = new EnrolmentFileRequest();

        request.rtoIdentifier = TOID;
        request.studentID = client.studentId();
        request.international = client.isInternational();
        request.concessionTypeIdentifier = requiredConcessionTypeIdentifier(client.studentId(), enrolment.concessionTypeIdentifier());
        request.trainingContractIdentifierApprenticeships = enrolment.trainingContractIdentifierApprenticeships();
        request.clientIdentifierApprenticeships = enrolment.clientIdentifierApprenticeships();
        request.studyReasonIdentifier = client.enrolmentInfo().studyReasonIdentifier;
        request.fundingSourceStateIdentifier = enrolment.fundingSourceStateIdentifier();

        request.startDate = toISO(enrolment.startDate());
        request.endDate = toISO(enrolment.endDate());
        request.courseStartedDate = toISO(enrolment.courseStartedDate());
        request.nominalHour = enrolment.nominalHour();
        request.tuitionFee = enrolment.tuitionFee();
        request.outcomeIdentifier = enrolment.getOutcomeIdentifier().code();  // Result not yet available (Code 90)
        request.unitCode = enrolment.getUnitCode();
        request.courseIdentifier = enrolment.courseEnrolled().courseIdentifier();
        request.hoursAttended = hoursAttended(client.studentId(), enrolment.hoursAttended(), enrolment.getOutcomeIdentifier());
        request.associatedCourseIdentifier = enrolment.associatedCourseIdentifier();
        // request.anzsicCode = enrolment.anzsicCode();
        request.enrolmentDate = enrolment.enrolmentDateObject();
        request.enrolmentIdentifier = client.enrolmentIdentifier(enrolment.getRowNum());
        request.clientFeesOther = enrolment.clientFeesOther();
        request.deliveryProviderABN = deliveryProviderABN();
        request.fundingEligibilityKey = "";
        request.programEnrolmentIdentifier =
                ProgramEnrolmentIdentifier.programEnrolmentIdentifier(PurchasingContractIdentifier.purchasingContractIdentifier(client.isInternational(), TOID), client.studentId(), enrolment.studentCourse().getCourseIdentifier(), Dates.toLocalDate(enrolment.studentCourse().courseStart()));

       return request;
    }

    private String hoursAttended(String sid, String aHoursAttended, OutcomeIdentifierNational outcomeIdentifierNational) {
        if (outcomeIdentifierNational.isWithdrawn()) {
            checkArgument(hasLength(aHoursAttended), "SID=%s HoursAttended is missing. If outcomeIdentifierNational is 40 (withdraw), HoursAttended must be reported", sid);
            return aHoursAttended;
        } else if (hasLength(aHoursAttended)) {
            logger.error("SID={} HoursAttended {} is ignored. Only applicable if outcomeIdentifierNational is 40 (withdraw)", sid, aHoursAttended);
        }
        return "";
    }

    private String requiredConcessionTypeIdentifier(String sid, String concessionTypeIdentifier) {
        checkArgument(isNotBlank(concessionTypeIdentifier), "SID=%s concessionTypeIdentifier must not be blank", sid);

        return concessionTypeIdentifier;
    }


    /**
     * Australian Business Number (ABN) of the organisation, contracted or subcontracted, delivering the training at a location.
     */
    private String deliveryProviderABN() {
        return deliveryProviderABN;
    }
}
