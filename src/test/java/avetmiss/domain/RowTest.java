package avetmiss.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RowTest {
    @Test
    public void values() throws Exception {
        Row row = new Row("a", "b");
        assertThat(row.values().length, is(2));
        assertThat(row.values()[0], is("a"));
        assertThat(row.values()[1], is("b"));
    }

    @Test
    public void valuesWithArray() throws Exception {
        Row row = new Row(new String[]{"a", "b"});
        assertThat(row.values().length, is(2));
        assertThat(row.values()[0], is("a"));
        assertThat(row.values()[1], is("b"));
    }

}