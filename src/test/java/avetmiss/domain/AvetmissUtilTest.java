package avetmiss.domain;

import avetmiss.util.LabelValue;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AvetmissUtilTest {

    @Test
    public void asLabelValue() throws Exception {
        Map<String, String> map = new LinkedHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");

        List<LabelValue> labelValues = AvetmissUtil.asLabelValue(map);
        assertThat(labelValues.size(), is(2));
        assertThat(labelValues.get(0).getValue(), is("key1"));
        assertThat(labelValues.get(0).getLabel(), is("value1"));
    }

    @Test
    public void toDate() throws Exception {
        LocalDate date = LocalDate.of(2016, 12, 1);
        assertEquals("01122016", AvetmissUtil.toDate(date));
    }
}