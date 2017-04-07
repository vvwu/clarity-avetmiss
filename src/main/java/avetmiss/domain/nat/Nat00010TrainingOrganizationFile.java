package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat00010TrainingOrganizationFileRequest;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;

import java.util.Arrays;
import java.util.List;

import static avetmiss.domain.AvetmissUtil.formattedRtoIdentifier;
import static avetmiss.domain.Field.of;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

/**
 * NAT00010 - Training Organisation file
 * <p>
 * Purpose: The Training Organisation (NAT00010) file provides details of
 * the organisation responsible for administering the information contained
 * in the collection files.
 * <p>
 * Description: This file contains a single record for information about the
 * training organisation that is providing the data.
 */
public class Nat00010TrainingOrganizationFile {

    public enum TrainingOrganisationTypeIdentifier {
        TAFE(31),
        LEARN_LOCAL_ORG(61),
        PRIVATE_RTO(91);

        private int code;
        private TrainingOrganisationTypeIdentifier(int code) {
            this.code = code;
        }

        public int code() {
            return code;
        }
    }

    private final static Header header = Header.Header(
            of("Training Organisation Identifier", 10),
            of("Training Organisation Name", 100),
            of("Training Organisation Type Identifier", 2),
            of("Address First Line", 50),
            of("Address Second Line", 50),
            of("Address Location - Suburb or Town or Locality", 50),
            of("Postcode", 4),
            of("State Identifier", 2),
            of("Contact Name", 60),
            of("Telephone Number", 20),
            of("Facsimile Number", 20),
            of("E-mail Address", 80),
            of("Software product name", 20),
            of("Software Vendor E-mail Address", 80));


    public String export(Nat00010TrainingOrganizationFileRequest request) {
        String addressSecondLine = "";
        String softwareProduct = "Clarity2008";
        String softwareVendorEmailAddress = "rock.yu99@gmail.com";

        String[] dataRow = new String[] {
                formattedRtoIdentifier(request.rtoIdentifier),
                request.organizationFullName,
                TrainingOrganisationTypeIdentifier.PRIVATE_RTO.code() + "",  // 91 = Education/training business or centre: Privately Operated Registered Training Organisation
                request.addressFirstLine,
                addressSecondLine,
                request.suburb,
                request.postcode,
                request.stateIdentifier,
                request.contactName,
                request.telephone,
                request.facsimile,
                request.contactEmail,
                softwareProduct,
                softwareVendorEmailAddress};

        return ExportHelper.writeToString(header.sizes(), asList(new Row(dataRow)));
    }

}
