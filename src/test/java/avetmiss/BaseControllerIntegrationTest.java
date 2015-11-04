package avetmiss;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public abstract class BaseControllerIntegrationTest {
    @Value("${local.server.port}")
    private int port;

    protected String base;
    protected RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = "http://localhost:" + port + "/avetmiss-service";
        template = new TestRestTemplate();
    }
}
