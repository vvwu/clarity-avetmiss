package avetmiss.infrastructure;

import avetmiss.domain.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FileBasedUnitRepositoryTest {
    private FileBasedUnitRepository instance;

    @Before
    public void setUp() throws Exception {
        this.instance = new FileBasedUnitRepository();
    }

    @Test
    public void testLoadNTISUnits() throws Exception {
        ensureThatUnitsIncludeRequiredExtras();
    }

    private void ensureThatUnitsIncludeRequiredExtras() {
        assertThatCompetencyExist("VBQU130");
        assertThatCompetencyExist("VPAU560");
        assertThatCompetencyExist("VPAU528");
        assertThatCompetencyExist("VPAU523");
        assertThatCompetencyExist("VPAM543");
        assertThatCompetencyExist("VPAU525");
        assertThatCompetencyExist("VPAU524");
        assertThatCompetencyExist("VPAU527");
        assertThatCompetencyExist("VPAU526");

        // 22255VIC
        assertThatCompetencyExist("VU21504");
        assertThatCompetencyExist("VU21503");
        assertThatCompetencyExist("VU21502");
        assertThatCompetencyExist("VU21501");
        assertThatCompetencyExist("VU21500");
        assertThatCompetencyExist("VU21499");
        assertThatCompetencyExist("VU21473");
        assertThatCompetencyExist("VU21472");
        assertThatCompetencyExist("VU21470");
        assertThatCompetencyExist("VU21323");
        assertThatCompetencyExist("VU21354");

        // 22258VIC
        assertThatCompetencyExist("VU20760");
        assertThatCompetencyExist("VU20763");
        assertThatCompetencyExist("VU20766");
        assertThatCompetencyExist("VU20746");
        assertThatCompetencyExist("VU21353");
        assertThatCompetencyExist("VU21508");
        assertThatCompetencyExist("VU21509");
        assertThatCompetencyExist("VU21510");
        assertThatCompetencyExist("VU21511");
        assertThatCompetencyExist("VU21512");
        assertThatCompetencyExist("VU21513");
        assertThatCompetencyExist("VU21514");

        // 22251VIC
        assertThatCompetencyExist("VU21297");
        assertThatCompetencyExist("VU21456");
        assertThatCompetencyExist("VU21457");
        assertThatCompetencyExist("VU21458");
        assertThatCompetencyExist("VU21459");
        assertThatCompetencyExist("VU21460");
        assertThatCompetencyExist("VU21461");
        assertThatCompetencyExist("VU21462");
        assertThatCompetencyExist("VU21463");
        assertThatCompetencyExist("VU21464");
        assertThatCompetencyExist("BSBITU201A");
        assertThatCompetencyExist("ICAICT103A");

        // 10362 NAT Certificate I in Spoken and Written English:
        assertThatCompetencyExist("SWELRN101A");
        assertThatCompetencyExist("SWEPER102A");
        assertThatCompetencyExist("SWETRA103A");
        assertThatCompetencyExist("SWEMSG107A");
        assertThatCompetencyExist("SWEINS108A");
        assertThatCompetencyExist("SWEDES109A");
        assertThatCompetencyExist("SWETXT111A");

        assertThatCompetencyExist("SWEJOB214A");


        // Course in Preliminary Spoken and Written English (10361NAT)
        assertThatCompetencyExist("SWELRN001A");
        assertThatCompetencyExist("SWEDEC002A");
        assertThatCompetencyExist("SWELTR003A");
        assertThatCompetencyExist("SWENUM004A");
        assertThatCompetencyExist("SWESYM005A");
        assertThatCompetencyExist("SWEWRD006A");
        assertThatCompetencyExist("SWETIM007A");
        assertThatCompetencyExist("SWEPRN008A");
        assertThatCompetencyExist("SWEEXC009A");

        // Certificate II in Spoken and Written English (10363NAT)
        assertThatCompetencyExist("SWEINF204A");
        assertThatCompetencyExist("SWESTO209A");
    }

    private void assertThatCompetencyExist(String unitIdentifier) {
        Unit competency = this.instance.findByCode(unitIdentifier);
        assertThat(competency, is(notNullValue()));
        assertThat(competency.getDescription(), is(notNullValue()));
        assertThat("missing FieldOfEducationIdentifier: " + competency.getCode(),
                competency.getFieldOfEducationIdentifier(), is(notNullValue()));
    }
}
