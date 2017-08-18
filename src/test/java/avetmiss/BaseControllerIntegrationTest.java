package avetmiss;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "nat.output.dir=natOutput"})
@ActiveProfiles("test")
public abstract class BaseControllerIntegrationTest {
    @Value("${local.server.port}")
    private int port;

    protected String base;
    protected RestTemplate template;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        createTableAndData();

        this.base = "http://localhost:" + port + "/avetmiss-service";
        template = new TestRestTemplate();
    }

    private void createTableAndData() {
        execute("drop table if exists unit");
        execute("create table unit (" +
                "  id int generated always as IDENTITY(START WITH 1)," +
                "  code varchar(40) not null," +
                "  name varchar(100) not null," +
                "  field_of_education_identifier varchar(20)" +
                ")");
    }

    protected JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }

    protected void execute(String sql) {
        jdbcTemplate().update(sql);
    }
}
