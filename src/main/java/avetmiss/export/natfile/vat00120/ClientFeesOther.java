package avetmiss.export.natfile.vat00120;

import avetmiss.client.payload.StudentCourseEnrolmentInfoReadModel;
import avetmiss.client.payload.StudentCourseReadModel;
import avetmiss.domain.AvetmissConstant;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.base.Preconditions.checkArgument;

public class ClientFeesOther {

    /**
     * This fee/cost is the non- tuition fee and may include such things as materials and equipment costs charged to
     * the students as part of an enrolment requirement when undertaking training.
     *
     * The Client Fees – Other amount is to be entered as a rounded up dollar amount and the same amount must be
     * reported against every unique combination of Client, Program (Qualification/Course) Identifier and Course
     * Commencement Date enrolment.
     *
     * This field must be blank for non-government subsidised training and 00000 where no such fees/costs are charged.
     */
    public static String clientFeesOther(StudentCourseReadModel studentCourse) {
        StudentCourseEnrolmentInfoReadModel studentCourseEnrolmentInfo = studentCourse.enrolmentInfo;

        String fundingSourceStateIdentifier = studentCourseEnrolmentInfo.fundingSourceStateIdentifier;
        if (AvetmissConstant.isFeeForServiceFundingSourceStateIdentifier(fundingSourceStateIdentifier)) {
            return "";
        }

        return to5DigitsDisplayString(studentCourse.otherFees);
    }

    private static String to5DigitsDisplayString(BigDecimal fee) {
        checkArgument(fee.compareTo(BigDecimal.valueOf(99999.0)) <= 0, "fee must be less than 100,000");
        int value = fee.setScale(0, RoundingMode.HALF_UP).intValue();
        return StringUtils.leftPad(value + "", 5, "0");
    }
}
