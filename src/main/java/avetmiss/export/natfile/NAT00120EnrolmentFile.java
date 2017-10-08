package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.EnrolmentFileRequest;
import avetmiss.export.Client;
import avetmiss.export.Enrolment;
import avetmiss.util.DateUtil;
import avetmiss.util.Dates;
import avetmiss.util.hudson.TaskListener;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class NAT00120EnrolmentFile {

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

                    EnrolmentFileRequest enrolmentFileRequest = oneEnrolmentFileRequestRow(client, enrolment, TOID, out);
                    rows.add(enrolmentFileRequest);
                } catch (Exception e) {
                    throw new IllegalArgumentException(format("NAT00120EnrolmentFile failed at enrolment, rowNum: %s", enrolment.getRowNum()), e);
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
    private EnrolmentFileRequest oneEnrolmentFileRequestRow(Client client, Enrolment enrolment, String TOID, TaskListener out) {
        EnrolmentFileRequest request = new EnrolmentFileRequest();

        request.rtoIdentifier = TOID;
        request.studentID = client.studentId();
        request.concessionTypeIdentifier = requiredConcessionTypeIdentifier(out, client.studentId(), enrolment.concessionTypeIdentifier());
        request.trainingContractIdentifierApprenticeships = enrolment.trainingContractIdentifierApprenticeships();
        request.clientIdentifierApprenticeships = enrolment.clientIdentifierApprenticeships();
        request.studyReasonIdentifier = client.enrolmentInfo().studyReasonIdentifier;
        request.fundingSourceStateIdentifier = enrolment.fundingSourceStateIdentifier();

        request.startDate = Dates.toISO(enrolment.startDate());
        request.endDate = Dates.toISO(enrolment.endDate());
        request.courseStartedDate = DateUtil.toISO(enrolment.courseStartedDate());
        request.nominalHour = enrolment.nominalHour();
        request.tuitionFee = enrolment.tuitionFee();
        request.outcomeIdentifier = enrolment.getOutcomeIdentifier().code();  // Result not yet available (Code 90)
        request.unitCode = enrolment.getUnitCode();
        request.courseIdentifier = enrolment.courseEnrolled().courseIdentifier();
        request.hoursAttended = enrolment.hoursAttended();
        // request.anzsicCode = enrolment.anzsicCode();
        request.enrolmentDate = DateUtil.toISO(enrolment.enrolmentDateObject());
        request.enrolmentIdentifier = client.enrolmentIdentifier(enrolment.getRowNum());
        request.clientFeesOther = enrolment.clientFeesOther();
        request.deliveryProviderABN = deliveryProviderABN();
        request.fundingEligibilityKey = "";

       return request;
    }

    private String requiredConcessionTypeIdentifier(TaskListener out, String sid, String concessionTypeIdentifier) {
        if(StringUtils.isBlank(concessionTypeIdentifier)) {
            out.error("SID=" + sid + " concessionTypeIdentifier must not be blank");
        }
        return concessionTypeIdentifier;
    }


    /**
     * Australian Business Number (ABN) of the organisation, contracted or subcontracted, delivering the training at a location.
     */
    private String deliveryProviderABN() {
        return deliveryProviderABN;
    }
}
