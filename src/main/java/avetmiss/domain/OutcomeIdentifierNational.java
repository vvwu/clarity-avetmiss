package avetmiss.domain;

import com.google.common.collect.Maps;

import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;

public class OutcomeIdentifierNational {
    private final static Map<Integer, String> identifiers = Maps.newLinkedHashMap();
    static {
        identifiers.put(20, "Competency achieved/pass");
        identifiers.put(30, "Competency not achieved/fail");
        identifiers.put(40, "Withdrawn/discontinued");
        identifiers.put(50, "Recognition of Prior Learning – assessment started and result not yet available");
        identifiers.put(51, "Recognition of Prior Learning - granted");
        identifiers.put(52, "Recognition of Prior Learning - not granted");
        identifiers.put(53, "Recognition of Current Competency - granted");
        identifiers.put(54, "Recognition of Current Competency - not granted");
        identifiers.put(60, "Credit transfer/national recognition");
        identifiers.put(61, "Superseded subject");
        identifiers.put(70, "Continuing enrolment - Activity End Date (Enrolment Activity End Date) occurs in a future collection year");
        identifiers.put(90, "Result not yet available - Activity End Date (Enrolment Activity End Date) is in the current collection year");
        identifiers.put(81, "Non-assessable enrolment - Satisfactorily completed");
        identifiers.put(82, "Non-assessable enrolment – Withdrawn or not satisfactorily completed");
    }

    private final static int OUTCOME_IDENTIFIER_WITHDRAWN = 40;
    private final static int CONTINUING_ENROLMENT_IN_THE_CURRENT_COLLECTION_YEAR = 90;
    public final static int CONTINUING_ENROLMENT_IN_THE_FUTURE_COLLECTION_YEAR = 70;

    private int code;
    public OutcomeIdentifierNational(String codeString) {
        if(isBlank(codeString)) {
            throw new IllegalArgumentException("OutcomeIdentifierNational is required");
        }

        int aCode;
        try {
            aCode = Integer.parseInt(codeString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("OutcomeIdentifierNational value must be an integer: " + codeString);
        }

        if(!identifiers.containsKey(aCode)) {
            throw new IllegalArgumentException("OutcomeIdentifierNational code is invalid:" + aCode + ", only support: " + identifiers.keySet());
        }

        this.code = aCode;
    }

    public int code() {
        return code;
    }

    public boolean is90ContinuingEnrolmentInTheCurrentCollectionYear() {
        return code == CONTINUING_ENROLMENT_IN_THE_CURRENT_COLLECTION_YEAR;
    }

    public boolean isWithdrawn() {
        return OUTCOME_IDENTIFIER_WITHDRAWN == code();
    }
}
