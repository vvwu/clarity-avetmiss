package avetmiss.infrastructure;

import avetmiss.domain.Suburb;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonSuburbRepositoryIntegrationTest {

    private JsonSuburbRepository instance;

    @Before
    public void setUp() throws Exception {
        this.instance = new JsonSuburbRepository("suburbs.json");
    }

    @Test
    public void getSuburbs() throws Exception {
        List<Suburb> suburbs = this.instance.getSuburbs(3011);
        assertThat(suburbs.size(), is(3));
        assertThat(suburbs.get(0), is(new Suburb("Footscray", 3011)));
        assertThat(suburbs.get(1), is(new Suburb("Seddon", 3011)));
    }
}
