package avetmiss.controller;

import avetmiss.BaseControllerIntegrationTest;
import avetmiss.controller.payload.inputFile.AvetmissInputFileProcessResult;
import avetmiss.controller.payload.inputFile.EnrolmentRowReadModel;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ImportNtisUnitsControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Test
    public void post() throws Exception {

        String text = getNtisUnitTextWith100Units();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>(text, headers);

        String result = template.postForEntity(base + "/units/importUnits", requestEntity, String.class).getBody();

        assertThat(result, is("added: 100, ignored: 0, total count before import: 0, total count after import: 100"));

        String result2 = template.postForEntity(base + "/units/importUnits", requestEntity, String.class).getBody();
        assertThat(result2, is("added: 0, ignored: 100, total count before import: 100, total count after import: 100"));
    }

    @Test
    public void postWithIncorrectInputText() throws Exception {
        String text = getIncorrectNtisUnitText();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>(text, headers);

        ResponseEntity<String> response = template.postForEntity(base + "/units/importUnits", requestEntity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody().startsWith("java.lang.IllegalArgumentException: Can not read line: SWEEXC009A, lineNum: 1"), is(true));
    }

    private String getNtisUnitTextWith100Units() throws IOException {
        Resource resource = new ClassPathResource("ntis_unit_100.txt");
        return CharStreams.toString(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }

    private String getIncorrectNtisUnitText() throws IOException {
        return "code\tname\tfield of ed\n" +
                "SWEEXC009A";
    }

}
