package avetmiss.domain;

import static avetmiss.util.StringUtil.isBlank;

public class StudentPostCode {

    public static String postCode(String studentID, String aPostcode) {
        String postCode = aPostcode;
        if(isBlank(postCode)) {
            return "@@@@";
        } else if(Integer.valueOf(postCode) > 9999 || Integer.valueOf(postCode) < 1 || postCode.length() != 4) {
            System.err.println(String.format("[Export Client]: student '%s' data error: '%s' is not a valid value for 'PostCode', " +
                    "valid range is [0001, 9999]", studentID, postCode));
            return "0000"; // post code unknown
        }
        return postCode;
    }
}
