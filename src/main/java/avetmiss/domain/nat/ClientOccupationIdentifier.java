package avetmiss.domain.nat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClientOccupationIdentifier {

    private final static Set<String> LABOUR_FORCE_STATUS_EXCEPTIONS = new HashSet(Arrays.asList("06", "07", "08", "@@"));
    private final static int CLERICAL_AND_ADMINISTRATIVE_WORKERS = 5;
    private final static int CLIENT_OCCUPATION_IDENTIFIER_DEFAULT = CLERICAL_AND_ADMINISTRATIVE_WORKERS;

    public static String clientOccupationIdentifier(String labourForceStatus) {
        /**
         * The Client Occupation Identifier may be blank if the student has indicated that their Labour Force Status is;
         * 06 – UNEMPLOYED – SEEKING FULL TIME WORK,
         * 07 – UNEMPLOYED – SEEKING PART-TIME WORK,
         * 08 – NO EMPLOYED NOT SEEKING WORK,
         * @@ - NOTE STATED.
         */

        return LABOUR_FORCE_STATUS_EXCEPTIONS.contains(labourForceStatus) ? " " : (CLIENT_OCCUPATION_IDENTIFIER_DEFAULT + "");
    }
}
