package avetmiss.domain.nat;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ClientIndustryOfEmployment {

    private final static String OTHER_SERVICES = "S";
    private final static Set<String> LABOUR_FORCE_STATUS_EXCEPTIONS = newHashSet("06", "07", "08", "@@");

    public static String clientIndustryOfEmployment(String labourForceStatus) {
        // The Client Industry of Employment field may be blank when the student has indicated that their Labour Force Status is;
        // 06 – UNEMPLOYED – SEEKING FULL TIME WORK,
        // 07 – UNEMPLOYED – SEEKING PART-TIME WORK,
        // 08 – NOT EMPLOYED NOT SEEKING WORK ,
        // @@ - NOT STATED.

        return (LABOUR_FORCE_STATUS_EXCEPTIONS.contains(labourForceStatus)) ? " " : OTHER_SERVICES;
    }
}
