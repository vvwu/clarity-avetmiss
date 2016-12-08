package avetmiss.domain;

import avetmiss.util.LabelValue;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AvetmissUtilTest {

    @Test
    public void asLabelValue() throws Exception {
        Map<String, String> map = newLinkedHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");

        List<LabelValue> labelValues = AvetmissUtil.asLabelValue(map);
        assertThat(labelValues.size(), is(2));
        assertThat(labelValues.get(0).getValue(), is("key1"));
        assertThat(labelValues.get(0).getLabel(), is("value1"));
    }
}