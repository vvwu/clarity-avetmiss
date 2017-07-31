package avetmiss.export.natfile;

import avetmiss.client.payload.EnrolmentInfoReadModel;
import avetmiss.controller.payload.nat.ClientFileRequest;
import avetmiss.export.Client;
import avetmiss.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class NAT00080ClientFile {

    public List<ClientFileRequest> clientFileRequest(List<Client> clients) {
        List<ClientFileRequest> requests = new ArrayList<>();
        for (Client client : clients) {
            try {
                requests.add(toClientFileRequest(client));
            } catch (Exception e) {
                throw new IllegalArgumentException(format("NAT00080ClientFile failed at studentID: %s", client.studentId()), e);
            }
        }
        return requests;
    }

    private ClientFileRequest toClientFileRequest(Client client) {
        EnrolmentInfoReadModel enrolmentInfo = client.enrolmentInfo();

        ClientFileRequest request = new ClientFileRequest();
        request.studentID = client.studentId();
        request.usi = client.usi();
        request.vsn = client.vsn();

        request.firstName = client.firstName();
        request.lastName = client.lastName();

        request.highestSchoolLevelCompletedIdentifier = enrolmentInfo.highestSchoolLevelCompletedIdentifier;
        request.yearHighestSchoolLevelCompleted = enrolmentInfo.yearHighestSchoolLevelCompleted;
        request.sex = client.sex();
        request.dateOfBirth = DateUtil.toISO(client.dateOfBirthObject());
        request.postCode = client.getPostCode();
        request.indigenousStatusIdentifier = enrolmentInfo.indigenousStatusIdentifier;
        request.mainLanguageSpokenAtHomeIdentifier = enrolmentInfo.mainLanguageSpokenAtHomeIdentifier;
        request.labourForceStatusIdentifier = enrolmentInfo.labourForceStatusIdentifier;
        request.countryIdentifier = enrolmentInfo.countryIdentifier;
        request.disabilityFlag = enrolmentInfo.disabilityFlag;
        request.atSchoolFlag = enrolmentInfo.atSchoolFlag;
        request.priorEducationalAchievementFlag = enrolmentInfo.priorEducationalAchievementFlag;
        request.proficiencyInSpokenEnglishIdentifier = enrolmentInfo.proficiencyInSpokenEnglishIdentifier;
        request.stateIdentifier = enrolmentInfo.stateIdentifier;
        request.suburb = client.suburb();

        request.addressBuildingName = client.address().addressBuildingName();
        request.addressFlatOrUnitDetails = client.address().addressFlatOrUnitDetails();
        request.addressStreetNumber = client.address().addressStreetNumber();
        request.addressStreetName = client.address().addressStreetName();

        // NAT00085ClientPostalDetailsFile only
        request.title = client.title();
        request.contactPhoneNo = client.contactPhoneNo();
        request.contactMobile = client.contactMobile();
        request.contactEmailAddress = client.contactEmailAddress();

        return request;
    }
}
