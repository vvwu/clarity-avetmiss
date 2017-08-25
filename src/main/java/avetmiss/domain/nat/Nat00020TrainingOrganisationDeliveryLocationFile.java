package avetmiss.domain.nat;

import avetmiss.domain.AvetmissUtil;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;

import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static java.util.Arrays.asList;

public class Nat00020TrainingOrganisationDeliveryLocationFile {
    public final static String TOID_VIT = "20829";
    public final static String DELIVERY_LOCATION_IDENTIFIER_QUEEN_STREET = "CITY";
    public static final String STATE_IDENTIFIERS_VIC = "02";

    private enum CountryIdentifier {
        AUSTRALIA_INCLUDES_EXTERNAL_TERRITORIES("1100"),
        AUSTRALIA("1101"),
        NORFOLK_ISLAND("1102"),
        AUSTRALIAN_EXTERNAL_TERRITORIES("1199");

        CountryIdentifier(String identifier) {
            this.identifier = identifier;
        }
        public final String identifier;
    }

    private final static Header header = Header(
            of("Training Organisation Identifier", 10),
            of("Training Organisation Delivery Location Identifier", 10),
            of("Training Organisation Delivery Location Name", 100),
            of("Postcode", 4),
            of("State Identifier", 2),
            of("Address Location - Suburb, Locality or Town", 50),
            of("Country Identifier", 4),
            of("Address Building/Property Name", 50),
            of("Address Flat/Unit Details", 30),
            of("Address Street Number", 15),
            of("Address Street Name", 70));

    public String export(String rtoIdentifier) {
        List<Row> rows = exportRaw(rtoIdentifier);
        return ExportHelper.writeToString(header.sizes(), rows);
    }

    public List<Row> exportRaw(String rtoIdentifier) {
        boolean vit = TOID_VIT.equals(rtoIdentifier);

        String addressBuildingName = null;
        String addressFlatOrUnitDetails = vit ? "Level 10" : "Level 3";
        String addressStreetNumber = vit ? "123" : "118";
        String addressStreetName = "Queen Street";
        String locationIdentifier = "CITY";

        Row city = new Row(
                AvetmissUtil.formattedRtoIdentifier(rtoIdentifier),
                locationIdentifier,
                DELIVERY_LOCATION_IDENTIFIER_QUEEN_STREET,
                "3000",
                STATE_IDENTIFIERS_VIC,
                "Melbourne",
                CountryIdentifier.AUSTRALIA.identifier,
                addressBuildingName,
                addressFlatOrUnitDetails,
                addressStreetNumber,
                addressStreetName);

        return asList(city);
    }
}
