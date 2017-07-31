package avetmiss.client.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentCourseReadModel {
    public int studentCourseID;
    public int courseID;
    public String status;
    public boolean qualificationIssued;
    public boolean deferred;
    public String supersededOldCourseIdentifier;
    public BigDecimal otherFees;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public Date startDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public Date endDate;

    public StudentCourseEnrolmentInfoReadModel enrolmentInfo;
}
