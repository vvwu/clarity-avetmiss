package avetmiss.infrastructure.unitFile;

import avetmiss.domain.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NtisUnitTextLineExtractorTest {

    private NtisUnitTextLineExtractor extractor;

    private final String line = "023/03\tAddress legal and administrative requirements\t090901";

    @Before
    public void setup() {
        this.extractor = new NtisUnitTextLineExtractor();
    }

    @Test
    public void shouldExtractUnitFromLine() {
        Unit unit = extractor.toCompetency(line);
        assertThat(unit.code(), is("023/03"));
        assertThat(unit.name(), is("Address legal and administrative requirements"));
        assertThat(unit.fieldOfEducationIdentifier(), is("090901"));
    }
}
