package avetmiss.controller;

import avetmiss.BaseControllerIntegrationTest;
import avetmiss.controller.payload.inputFile.AvetmissInputFileProcessResult;
import avetmiss.controller.payload.inputFile.EnrolmentRowReadModel;
import avetmiss.domain.Unit;
import avetmiss.domain.UnitRepository;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AvetmissInputFileReaderControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        unitRepository.save(new Unit("BSBINN601B", "Manage organisational change", "080307"));
        unitRepository.save(new Unit("BSBADM405B", "Organise meetings", "080901"));
        unitRepository.save(new Unit("BSBCMM401A", "Make a presentation", "100707"));
    }

    @Test
    public void post() throws Exception {

        String csvContent = goodInputCsvContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>(csvContent, headers);

        AvetmissInputFileProcessResult result = template.postForEntity(base + "/readInput", requestEntity, AvetmissInputFileProcessResult.class).getBody();
        assertThat(result.clients.size(), is(2));
        assertThat(result.clients.get(0).enrolments.size(), is(1));
        assertThat(result.clients.get(1).enrolments.size(), is(2));
        assertThat(result.taskListener.errorList.size(), is(0));

        EnrolmentRowReadModel enrolmentRowReadModel = result.clients.get(0).enrolments.get(0);
        assertThat(enrolmentRowReadModel.rowNum, is(2));
        assertThat(enrolmentRowReadModel.studentId, is(50038));
        assertThat(enrolmentRowReadModel.studentName, is("Syed MUSTAFA"));
        assertThat(enrolmentRowReadModel.courseCode, is("BSB60207"));
        assertThat(enrolmentRowReadModel.startDate, is("2015-02-16"));
        assertThat(enrolmentRowReadModel.endDate, is("2015-05-20"));
        assertThat(enrolmentRowReadModel.hoursAttended, is(nullValue()));
        assertThat(enrolmentRowReadModel.outcomeIdentifier, is(30));
        assertThat(enrolmentRowReadModel.tuitionFee, is("420"));
        assertThat(enrolmentRowReadModel.unitCode, is("BSBINN601B"));
        assertThat(enrolmentRowReadModel.subjectName, is("Manage organisational change"));
        assertThat(enrolmentRowReadModel.fieldOfEducationIdentifier, is("080307"));
        assertThat(enrolmentRowReadModel.nominalHour, is(60));
        assertThat(enrolmentRowReadModel.supervisedHours, is(10));
    }

    private String goodInputCsvContent() throws IOException {
        Resource expectedJson = new ClassPathResource("good-input.csv");
        return CharStreams.toString(new InputStreamReader(expectedJson.getInputStream(), Charsets.UTF_8));
    }

}
