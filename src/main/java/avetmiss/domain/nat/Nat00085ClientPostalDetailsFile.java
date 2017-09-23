package avetmiss.domain.nat;


import avetmiss.client.payload.EnrolmentInfoReadModel;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;
import avetmiss.domain.StudentPostCode;
import avetmiss.export.Client;

import java.util.ArrayList;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;

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


    public String export(List<Client> requests) {
        List<Row> rows = new ArrayList();
        for (Client client: requests) {
            rows.add(exportOneRow(client));
        }

        return ExportHelper.writeToString(header, rows);
    }

    private Row exportOneRow(Client client) {
        String studentID = client.studentId();

        String addressPostalDeliveryBox = null;
        String telephoneNumberWork = null;
        EnrolmentInfoReadModel enrolmentInfo = client.enrolmentInfo();

        return new Row(
                studentID,
                client.title(),
                client.firstName(),
                client.lastName(),
                client.address().addressBuildingName(),
                client.address().addressFlatOrUnitDetails(),
                client.address().addressStreetNumber(),
                client.address().addressStreetName(),
                addressPostalDeliveryBox,
                client.suburb(),  // Address Location - Suburb, Locality or Town
                StudentPostCode.postCode(studentID, client.getPostCode()),
                enrolmentInfo.stateIdentifier,
                client.contactPhoneNo(),
                telephoneNumberWork,
                client.contactMobile(),
                client.contactEmailAddress());
    }
}
