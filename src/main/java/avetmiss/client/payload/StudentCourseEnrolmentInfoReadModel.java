package avetmiss.client.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentCourseEnrolmentInfoReadModel {
    public String fundingSourceNationalIdentifier;
    public String fundingSourceStateIdentifier;

    // FH (Fee Help) | (VFH) VET Fee Help | (SF) Self Funded | "" Not Applicable
    public String domesticFeeForServiceTypeDisplayValue;

    public String concessionTypeIdentifier;
    public String anzsicCode;
    public String trainingContractIdentifierApprenticeships;
    public String clientIdentifierApprenticeships;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public Date supervisedTeachingActivityCompletionDate;
}
