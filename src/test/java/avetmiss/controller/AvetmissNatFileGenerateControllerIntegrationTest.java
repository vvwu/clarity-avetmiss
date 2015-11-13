package avetmiss.controller;

import avetmiss.BaseControllerIntegrationTest;
import avetmiss.controller.payload.nat.*;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AvetmissNatFileGenerateControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Test
    public void getNatFiles() throws Exception {
        NatFilesRequest natFilesRequest = new NatFilesRequest();
        natFilesRequest.nat0009DisabilityFileRequests = asList(
                nat0009DisabilityFileRequest("300020", asList("11", "13")),
                nat0009DisabilityFileRequest("300041", asList("13")));

        natFilesRequest.nat00060SubjectFileRequests = asList(
                nat00060SubjectFileRequest("SWESTO209A", 50),
                nat00060SubjectFileRequest("SWEINF204A", 46));

        natFilesRequest.nat00100PriorEducationFileRequests = asList(
                nat00100PriorEducationFileRequest("300020", "420", "A"),
                nat00100PriorEducationFileRequest("300041", "524", "A"));

        natFilesRequest.nat00130QualificationCompletedFileRequests = asList(nat00130QualificationCompletedFileRequest());
        natFilesRequest.nat00010TrainingOrganizationFileRequest = nat00010TrainingOrganizationFileRequest();

        ResponseEntity<NatFileReadModel> response = template.postForEntity(base + "/natFiles", natFilesRequest, NatFileReadModel.class);
        NatFileReadModel natFileReadModel = response.getBody();

        assertThat(natFileReadModel.nat00060, is(
                "CSWESTO209A  COMPREHENDING & COMPOSING STORY TEXTS                                                               120101Y0050\r\n" +
                "CSWEINF204A  COMPREHENDING & GIVING SPOKEN INFORMATION                                                           120101Y0046\r\n"));

        assertThat(natFileReadModel.nat00090, is(
                "300020    11\r\n" +
                "300020    13\r\n" +
                "300041    13\r\n"));

        assertThat(natFileReadModel.nat00100, is(
                "300020    420A\r\n" +
                "300041    524A\r\n"));

        assertThat(natFileReadModel.nat00130, is(
                "0000020829SIT50307  300020    2015Y25102014\r\n"));

        assertThat(natFileReadModel.nat00010, is(
                "0000020829Org Pty Ltd                                                                                         91110 City Rd                                                                                         Melbourne                                         300003Bob                                                         123456              abcdefg             contact@company.com                                                             Clarity2008         vvwu0830@gmail.com                                                              \r\n"));
    }

    private Nat0009DisabilityFileRequest nat0009DisabilityFileRequest(String studentID, List<String> disabilityTypeIdentifiers) {
        Nat0009DisabilityFileRequest nat0009DisabilityFileRequest = new Nat0009DisabilityFileRequest();
        nat0009DisabilityFileRequest.studentID = studentID;
        nat0009DisabilityFileRequest.disabilityTypeIdentifiers = disabilityTypeIdentifiers;
        return nat0009DisabilityFileRequest;
    }

    private Nat00060SubjectFileRequest nat00060SubjectFileRequest(String subjectIdentifier, int nominalHours) {
        Nat00060SubjectFileRequest nat00060SubjectFileRequest = new Nat00060SubjectFileRequest();
        nat00060SubjectFileRequest.subjectIdentifier = subjectIdentifier;
        nat00060SubjectFileRequest.nominalHours = nominalHours;
        return nat00060SubjectFileRequest;
    }

    private Nat00100PriorEducationFileRequest nat00100PriorEducationFileRequest(String studentID, String priorEducationalAchievementIdentifier, String priorEducationAchievementRecognitionIdentifier) {
        Nat00100PriorEducationFileRequest request = new Nat00100PriorEducationFileRequest();
        request.studentID = studentID;
        request.priorEducationalAchievementIdentifier = priorEducationalAchievementIdentifier;
        request.priorEducationAchievementRecognitionIdentifier = priorEducationAchievementRecognitionIdentifier;
        return request;
    }

    private Nat00130QualificationCompletedFileRequest nat00130QualificationCompletedFileRequest() {
        Nat00130QualificationCompletedFileRequest request = new Nat00130QualificationCompletedFileRequest();
        request.rtoIdentifier = "20829";
        request.courseIdentifier = "sit50307";
        request.studentID = "300020";
        request.courseStartDate = "2014-10-25";
        request.isQualificationIssued = true;
        request.isCourseCompleted = true;
        request.yearCourseEnd = 2015;

        return request;
    }

    private Nat00010TrainingOrganizationFileRequest nat00010TrainingOrganizationFileRequest() {
        Nat00010TrainingOrganizationFileRequest request = new Nat00010TrainingOrganizationFileRequest();
        request.rtoIdentifier = "20829";
        request.organizationFullName = "Org Pty Ltd";
        request.addressFirstLine = "110 City Rd";
        request.suburb = "Melbourne";
        request.postcode = "3000";
        request.stateIdentifier = "03";
        request.contactName = "Bob";
        request.telephone = "123456";
        request.facsimile = "abcdefg";
        request.contactEmail = "contact@company.com";
        return request;
    }
}
