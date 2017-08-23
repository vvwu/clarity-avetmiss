package avetmiss.domain.nat;


import avetmiss.controller.payload.nat.ClientFileRequest;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;

import java.util.ArrayList;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static avetmiss.util.StringUtil.isBlank;

public class Nat00085ClientPostalDetailsFile {

    private final static Header header = Header(477,
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
        List<Row> rows = new ArrayList();
        for (ClientFileRequest client: requests) {
            rows.add(exportOneRow(client));
        }

        return ExportHelper.writeToString(header.sizes(), rows);
    }

    private Row exportOneRow(ClientFileRequest clientFileRequest) {
        String studentID = clientFileRequest.studentID;

        String addressPostalDeliveryBox = null;
        String telephoneNumberWork = null;

        return new Row(
                clientFileRequest.studentID,
                clientFileRequest.title,
                clientFileRequest.firstName,
                clientFileRequest.lastName,
                clientFileRequest.addressBuildingName,
                clientFileRequest.addressFlatOrUnitDetails,
                clientFileRequest.addressStreetNumber,
                clientFileRequest.addressStreetName,
                addressPostalDeliveryBox,
                clientFileRequest.suburb,  // Address Location - Suburb, Locality or Town
                postCode(studentID, clientFileRequest.postCode),
                clientFileRequest.stateIdentifier,
                clientFileRequest.contactPhoneNo,
                telephoneNumberWork,
                clientFileRequest.contactMobile,
                clientFileRequest.contactEmailAddress);
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
