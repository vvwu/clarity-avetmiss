package avetmiss.controller;

import avetmiss.BaseControllerIntegrationTest;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AvetmissIdentifiersControllerIntegrationTest extends BaseControllerIntegrationTest {

    private String getResource(String path) {
        ResponseEntity<String> response = template.getForEntity(base + "/identifiers" + path, String.class);
        return response.getBody();
    }

    @Test
    public void getConcessionTypeIdentifiers() throws Exception {
        String resource = getResource("/concessionType");

        assertThat(resource, is(expectation("concessionTypeIdentifiers.json")));
    }

    @Test
    public void getLevelOfEducationIdentifiers() throws Exception {
        String resource = getResource("/levelOfEducation");

        assertThat(resource, is(expectation("levelOfEducationIdentifiers.json")));
    }

    @Test
    public void getDisabilityTypeIdentifiers() throws Exception {
        String resource = getResource("/disabilityType");

        assertThat(resource, is(expectation("disabilityTypeIdentifiers.json")));
    }

    @Test
    public void getLanguageIdentifiers() throws Exception {
        String resource = getResource("/language");

        assertThat(resource, is(expectation("languageIdentifiers.json")));
    }

    @Test
    public void getAnzsicCodes() throws Exception {
        String resource = getResource("/anzsicCode");

        assertThat(resource, is(expectation("anzsicCodeIdentifiers.json")));
    }

    @Test
    public void getFundingSourceStateIdentifiersGovernmentFunded() throws Exception {
        String resource = getResource("/fundingSourceStateIdentifiersGovernmentFunded");

        assertThat(resource, is(expectation("fundingSourceStateIdentifiersGovernmentFunded.json")));
    }

    @Test
    public void getFundingSourceStateIdentifier() throws Exception {
        String resource = getResource("/fundingSourceStateIdentifier/L");

        assertThat(resource, is("{\"label\":\"L - Diploma and above (Apprentice/Trainee)\",\"value\":\"L\"}"));

        ResponseEntity<String> notFound = template.getForEntity(base + "/fundingSourceStateIdentifier/xyz", String.class);
        assertThat(notFound.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getFundingSourceStateIdentifiersNonGovernmentFunded() throws Exception {
        String resource = getResource("/fundingSourceStateIdentifiersNonGovernmentFunded");

        assertThat(resource, is(expectation("fundingSourceStateIdentifiersNonGovernmentFunded.json")));
    }

    @Test
    public void getFundingSourceNationalIdentifiers() throws Exception {
        String resource = getResource("/fundingSourceNational");

        assertThat(resource, is(expectation("fundingSourceNationalIdentifiers.json")));
    }

    @Test
    public void getSchoolLevelCompletedIdentifiers() throws Exception {
        String resource = getResource("/schoolLevelCompleted");

        assertThat(resource, is(expectation("schoolLevelCompletedIdentifiers.json")));
    }

    @Test
    public void getPriorEducationalAchievementIdentifiers() throws Exception {
        String resource = getResource("/priorEducationalAchievement");

        assertThat(resource, is(expectation("priorEducationalAchievementIdentifiers.json")));
    }

    @Test
    public void getLabourForceStatusIdentifiers() throws Exception {
        String resource = getResource("/labourForceStatus");

        assertThat(resource, is(expectation("labourForceStatusIdentifiers.json")));
    }

    @Test
    public void getStudyReasonIdentifiers() throws Exception {
        String resource = getResource("/studyReason");

        assertThat(resource, is(expectation("studyReasonIdentifiers.json")));
    }

    @Test
    public void getStudyReasonByIdentifier() throws Exception {
        String resource = getResource("/studyReason/02");

        assertThat(resource, is("{\"label\":\"To develop my existing business\",\"value\":\"02\"}"));

        ResponseEntity<String> notFound = template.getForEntity(base + "/studyReason/xyz", String.class);
        assertThat(notFound.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getIndigenousStatusIdentifiers() throws Exception {
        String resource = getResource("/indigenousStatus");

        assertThat(resource, is(expectation("indigenousStatusIdentifiers.json")));
    }

    @Test
    public void getEnglishProficiencyIdentifiers() throws Exception {
        String resource = getResource("/englishProficiency");

        assertThat(resource, is(expectation("englishProficiencyIdentifiers.json")));
    }

    @Test
    public void getStateIdentifiers() throws Exception {
        String resource = getResource("/state");

        assertThat(resource, is(expectation("stateIdentifiers.json")));
    }

    private String expectation(String jsonFilename) throws IOException {
        Resource expectedJson = new ClassPathResource(String.format("/expectation/%s", jsonFilename));
        return CharStreams.toString(new InputStreamReader(expectedJson.getInputStream(), Charsets.UTF_8));
    }
}