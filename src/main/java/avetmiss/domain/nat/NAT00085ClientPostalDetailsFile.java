package avetmiss.domain.nat;


import avetmiss.controller.payload.nat.ClientFileRequest;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;

import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static avetmiss.util.StringUtil.isBlank;
import static com.google.common.collect.Lists.newArrayList;

public class NAT00085ClientPostalDetailsFile {

    private final static Header header = Header(
            of("Client (Student) Identifier", 10),
            of("Client Title", 4),
            of("Client First Given Name", 40),
            of("Client Last Name (Surname)", 40),
            of("Address Building/Property Name", 50),
            of("Address Flat/Unit Details", 30),
            of("Address Street Number", 15),
            of("Address Street Name", 70),
            of("Address Postal Delivery Box", 22),
            of("Address Postal - Suburb, Locality or Town", 50),
            of("Postcode", 4),
            of("State Identifier", 2),
            of("Telephone Number - Home", 20),
            of("Telephone Number - Work", 20),
            of("Telephone Number - Mobile", 20),
            of("E-mail Address", 80));


    public String export(List<ClientFileRequest> requests) {
        List<String[]> rows = newArrayList();

        for (ClientFileRequest client: requests) {
            String[] row = exportOneRow(client);
            rows.add(row);
        }

        return ExportHelper.writeToString(header.sizes(), rows);
    }

    private String[] exportOneRow(ClientFileRequest client) {
        String studentID = client.studentID;

        String addressPostalDeliveryBox = null;
        String telephoneNumberWork = null;

        return new String[] {
                client.studentID,
                client.title,
                client.firstName,
                client.lastName,
                client.addressBuildingName,
                client.addressFlatOrUnitDetails,
                client.addressStreetNumber,
                client.addressStreetName,
                addressPostalDeliveryBox,
                client.suburb,  // Address Location - Suburb, Locality or Town
                postCode(studentID, client.postCode),
                client.stateIdentifier,
                client.contactPhoneNo,
                telephoneNumberWork,
                client.contactMobile,
                client.contactEmailAddress};
    }

    public String postCode(String studentID, String aPostcode) {
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
