package avetmiss.controller;

import avetmiss.Application;
import avetmiss.BaseControllerIntegrationTest;
import avetmiss.controller.payload.inputFile.AvetmissInputFileProcessResult;
import avetmiss.controller.payload.inputFile.EnrolmentRowReadModel;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AvetmissInputFileReaderControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Test
    public void post() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>(goodInputCsvContent(), headers);

        AvetmissInputFileProcessResult result = template.postForEntity(base + "/readInput", requestEntity, AvetmissInputFileProcessResult.class).getBody();
        assertThat(result.clients.size(), is(2));
        assertThat(result.clients.get(0).enrolments.size(), is(1));
        assertThat(result.clients.get(1).enrolments.size(), is(2));
        assertThat(result.taskListener.errorList.size(), is(0));

        EnrolmentRowReadModel enrolmentRowReadModel = result.clients.get(0).enrolments.get(0);
        assertThat(enrolmentRowReadModel.rowNum, is(2));
        assertThat(enrolmentRowReadModel.studentId, is(50038));
        assertThat(enrolmentRowReadModel.studentName, is("Syed MUSTAFA"));
        assertThat(enrolmentRowReadModel.courseName, is("Advanced Diploma of Business (BSB60207)"));
        assertThat(enrolmentRowReadModel.startDate, is("2015-02-16"));
        assertThat(enrolmentRowReadModel.endDate, is("2015-05-20"));
        assertThat(enrolmentRowReadModel.hoursAttended, is(nullValue()));
        assertThat(enrolmentRowReadModel.outcomeIdentifier, is(30));
        assertThat(enrolmentRowReadModel.tuitionFee, is("420"));
        assertThat(enrolmentRowReadModel.unitCode, is("BSBINN601B"));
        assertThat(enrolmentRowReadModel.subjectName, is("Manage organisational change"));
        assertThat(enrolmentRowReadModel.fieldOfEducationIdentifier, is("080307"));
        assertThat(enrolmentRowReadModel.nominalHour, is(60));
    }

    private String goodInputCsvContent() throws IOException {
        Resource expectedJson = new ClassPathResource("good-input.csv");
        return CharStreams.toString(new InputStreamReader(expectedJson.getInputStream(), Charsets.UTF_8));
    }

}
