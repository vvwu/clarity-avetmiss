package avetmiss.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class StudentPostCode {
    private static Logger logger = LoggerFactory.getLogger(StudentPostCode.class);

    // From 1/1/2018 the value below is not valid: 0000 Postcode provided but unknown.
    public static String postCode(String studentID, String aPostcode) {
        String postCode = aPostcode;
        if(isNotBlank(postCode) && valid4DigitsPostCode(postCode)) {
            return postCode;
        }

        if(isNotBlank(postCode)) {
            logger.error("[Export Client]: student '{}' data error: '{}' is not a valid value for 'PostCode', valid range is [0001, 9999]", studentID, postCode);
        }

        return "@@@@";
    }

    private static boolean valid4DigitsPostCode(String postCode) {
        return (postCode.length() == 4) && Integer.valueOf(postCode) <= 9999 && Integer.valueOf(postCode) >= 1;
    }
}
