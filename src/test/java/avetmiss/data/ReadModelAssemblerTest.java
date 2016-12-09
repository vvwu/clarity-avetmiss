package avetmiss.data;

import avetmiss.controller.payload.LabelValueReadModel;
import avetmiss.controller.payload.SuburbReadModel;
import avetmiss.domain.Suburb;
import avetmiss.util.LabelValue;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReadModelAssemblerTest {

    @Test
    public void toLabelValueReadModels() throws Exception {
        List<LabelValue> domains =
                Arrays.asList(LabelValue.labelValue("L1", "V1"), LabelValue.labelValue("L2", "V2"));

        List<LabelValueReadModel> readModels = ReadModelAssembler.toLabelValueReadModels(domains);
        assertThat(readModels.size(), is(2));

        assertThat(readModels.get(0).label, is("L1"));
        assertThat(readModels.get(0).value, is("V1"));
    }

    @Test
    public void toSuburbReadModels() throws Exception {
        List<Suburb> domains =
                Arrays.asList(new Suburb("Clayton", 3168), new Suburb("Melbourne", 3000));

        List<SuburbReadModel> readModels = ReadModelAssembler.toSuburbReadModels(domains);
        assertThat(readModels.size(), is(2));

        assertThat(readModels.get(0).name, is("Clayton"));
        assertThat(readModels.get(0).postCode, is(3168));
    }
}