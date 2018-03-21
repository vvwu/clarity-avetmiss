package avetmiss.domain;

import java.time.LocalDate;

public class PurchasingContractIdentifier {

    /*
     General:
     The Purchasing Contract Identifier is unique to the RTO.
     The Purchasing Contract Identifier must be consistent with the year the student commenced their Program Enrolment.
     */
     public static String purchasingContractIdentifier(boolean international, LocalDate courseStartDate, String trainingOrganizationIdentifier) {
        if (international) {
            return null;
        }

        int year = courseStartDate.getYear();
        int yearToUse = (year <= 2016) ? year : 2017;

        // e.g: 2017208290
        return yearToUse + trainingOrganizationIdentifier + "0";
    }
}
