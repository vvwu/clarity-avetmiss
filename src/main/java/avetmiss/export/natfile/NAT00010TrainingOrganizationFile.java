package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.Nat00010TrainingOrganizationFileRequest;
import avetmiss.client.payload.OrganizationConstantReadModel;

import static avetmiss.domain.AvetmissConstant.STATE_IDENTIFIERS_VIC;

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
public class NAT00010TrainingOrganizationFile {

    public Nat00010TrainingOrganizationFileRequest trainingOrganizationFileRequest(OrganizationConstantReadModel organizationConstant) {
        Nat00010TrainingOrganizationFileRequest request = new Nat00010TrainingOrganizationFileRequest();

        request.rtoIdentifier = organizationConstant.toid + "";
        request.organizationFullName = organizationConstant.fullName;
        request.addressFirstLine = organizationConstant.address;
        request.suburb = organizationConstant.suburb;
        request.postcode = organizationConstant.postcode;
        request.stateIdentifier = STATE_IDENTIFIERS_VIC;
        request.contactName = organizationConstant.contactName;
        request.telephone = organizationConstant.contactPhone;
        request.facsimile = organizationConstant.contactFax;
        request.contactEmail = organizationConstant.contactEmail;

        return request;
    }
}
