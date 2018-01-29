package avetmiss.domain;

public class PurchasingContractIdentifier {
    public static String purchasingContractIdentifier(boolean international, String trainingOrganizationIdentifier) {
        if (international) {
            return null;
        }

        // e.g: 2017208290
        return "2017" + trainingOrganizationIdentifier + "0";
    }
}
