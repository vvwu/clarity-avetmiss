package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class AvetmissIdentifiersControllerTest {

    private AvetmissApplicationService mockAvetmissApplicationService;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.mockAvetmissApplicationService = mock(AvetmissApplicationService.class);
        mvc = MockMvcBuilders.standaloneSetup(new AvetmissIdentifiersController(mockAvetmissApplicationService)).build();
    }

    @Test
    public void getConcessionTypeIdentifiers() throws Exception {
        String expected =
        "[{\"label\":\"VCE Scholarship\",\"value\":\"G\"},{\"label\":\"Health Care Card\",\"value\":\"H\"},{\"label\":\"Job Seeker and concession card holder (covered by a current, relevant pensioner Concession Card, Health Care Card or Veteran's Gold Card)\",\"value\":\"J\"},{\"label\":\"Job Seeker AND NOT currently holding a relevant Pensioner Concession Card, Health Care Card or Veteran's Gold Card\",\"value\":\"K\"},{\"label\":\"Prisoner\",\"value\":\"M\"},{\"label\":\"Other\",\"value\":\"O\"},{\"label\":\"Pensioner Concession Card\",\"value\":\"P\"},{\"label\":\"Veteran Gold Card Concession\",\"value\":\"V\"},{\"label\":\"None\",\"value\":\"Z\"}]";

        mvc.perform(MockMvcRequestBuilders.get("/identifiers/concessionType").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    public void levelOfEducation() throws Exception {
        String expected =
            "[{\"label\":\"Graduate Diploma\",\"value\":\"211\"},{\"label\":\"Professional Specialist Qualification at Graduate Diploma Level\",\"value\":\"213\"},{\"label\":\"Graduate Certificate\",\"value\":\"221\"},{\"label\":\"Professional Specialist Qualification at Graduate Certificate Level\",\"value\":\"222\"},{\"label\":\"Bachelor Degree (Honours)\",\"value\":\"311\"},{\"label\":\"Bachelor Degree (Pass)\",\"value\":\"312\"},{\"label\":\"Advanced Diploma\",\"value\":\"411\"},{\"label\":\"Associate Degree\",\"value\":\"413\"},{\"label\":\"Diploma\",\"value\":\"421\"},{\"label\":\"Certificate IV\",\"value\":\"511\"},{\"label\":\"Certificate III\",\"value\":\"514\"},{\"label\":\"Certificate II\",\"value\":\"521\"},{\"label\":\"Certificate I\",\"value\":\"524\"},{\"label\":\"Year 12\",\"value\":\"611\"},{\"label\":\"Year 11\",\"value\":\"613\"},{\"label\":\"Year 10\",\"value\":\"621\"},{\"label\":\"Other Non-award Courses\",\"value\":\"912\"},{\"label\":\"Statement of Attainment Not Identifiable by Level\",\"value\":\"991\"},{\"label\":\"Bridging and Enabling Courses Not Identifiable by Level\",\"value\":\"992\"},{\"label\":\"Education not elsewhere classified\",\"value\":\"999\"}]";

        mvc.perform(MockMvcRequestBuilders.get("/identifiers/levelOfEducation").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }
}