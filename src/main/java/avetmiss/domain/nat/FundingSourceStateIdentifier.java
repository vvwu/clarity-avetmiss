package avetmiss.domain.nat;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class FundingSourceStateIdentifier {

    private final static Map<String, String> stateToNationalCode = new HashMap();
    static {
        stateToNationalCode.put("F", "30");
        stateToNationalCode.put("L", "11");
        stateToNationalCode.put("P", "11");
        stateToNationalCode.put("S", "20");
        stateToNationalCode.put("PSG", "11");
        stateToNationalCode.put("LSG", "11");
        stateToNationalCode.put("YRP", "11");
        stateToNationalCode.put("YRL", "11");
        stateToNationalCode.put("RWP", "11");
        stateToNationalCode.put("RWL", "11");
    }

    public static String fundingSourceNationalIdentifierFrom(String fundingSourceStateIdentifier) {
        validateFundingSourceStateIdentifier(fundingSourceStateIdentifier);

        return stateToNationalCode.get(fundingSourceStateIdentifier);
    }

    private static void validateFundingSourceStateIdentifier(String fundingSourceStateIdentifier) {
        if(!stateToNationalCode.containsKey(fundingSourceStateIdentifier)) {
            throw new IllegalArgumentException(
                    format("fundingSourceStateIdentifier %s is not valid, use any of %s instead",
                            fundingSourceStateIdentifier, stateToNationalCode.keySet()));
        }
    }
}
