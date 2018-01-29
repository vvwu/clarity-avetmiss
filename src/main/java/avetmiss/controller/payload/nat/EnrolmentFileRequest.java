package avetmiss.controller.payload.nat;

import java.time.LocalDate;

public class EnrolmentFileRequest {
    public String rtoIdentifier;

    public String studentID;
    public boolean international;
    public String concessionTypeIdentifier;
    public String trainingContractIdentifierApprenticeships;
    public String clientIdentifierApprenticeships;
    public String studyReasonIdentifier;
    public String fundingSourceStateIdentifier;

    public String startDate;
    public String endDate;
    public String courseStartedDate;
    public int nominalHour;
    public String tuitionFee;
    public int outcomeIdentifier;
    public String unitCode;
    public String courseIdentifier;
    public String hoursAttended;
    public String associatedCourseIdentifier;
    // public String anzsicCode;
    public LocalDate enrolmentDate;
    public String enrolmentIdentifier;
    public int clientFeesOther;
    public String deliveryProviderABN;
    public String fundingEligibilityKey;
}
