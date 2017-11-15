package avetmiss.domain.nat;

import java.time.LocalDate;
import java.util.Arrays;

import avetmiss.controller.payload.nat.EnrolmentFileRequest;
import avetmiss.domain.OutcomeIdentifierNational;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Nat00120EnrolmentFileTest {
    private Nat00120EnrolmentFile instance = new Nat00120EnrolmentFile();

    @Test
    public void testExport() throws Exception {
        String export = instance.export(Arrays.asList(request()));
        assertThat(export, is("CITY      300020    BSBCMN407A  053261G   15012015230220111070140 114CONT2     Client1" +
                "   01N          NYCP  1500G2015208290     0251SIT30813  " +
                "02012015NN  05122014s30234-c9999999-uBSBCMN407A                       00230ABN-Number           \r\n"));
    }

    private EnrolmentFileRequest request() {
        EnrolmentFileRequest request = new EnrolmentFileRequest();
        request.rtoIdentifier = "20829";
        request.studentID = "300020";
        request.concessionTypeIdentifier = "G";
        request.trainingContractIdentifierApprenticeships = "CONT2";
        request.clientIdentifierApprenticeships = "Client1";
        request.studyReasonIdentifier = "01";
        request.fundingSourceStateIdentifier = "P";

        request.startDate = "2015-01-15";
        request.endDate = "2011-02-23";
        request.courseStartedDate = "2015-01-02";
        request.nominalHour = 140;
        request.tuitionFee = "1500";
        request.outcomeIdentifier = OutcomeIdentifierNational.CONTINUING_ENROLMENT_IN_THE_FUTURE_COLLECTION_YEAR;
        request.unitCode = "BSBCMN407A";
        request.courseIdentifier = "053261G";
        request.associatedCourseIdentifier = "sit30813";
        request.hoursAttended = "251";
        request.enrolmentDate = LocalDate.of(2014, 12, 5);;
        request.enrolmentIdentifier = "s30234-c9999999-uBSBCMN407A";
        request.clientFeesOther = 230;
        request.deliveryProviderABN = "ABN-Number";

        return request;
    }
}
