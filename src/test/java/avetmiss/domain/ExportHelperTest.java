package avetmiss.domain;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ExportHelperTest {

    @Test
    public void writeToString() throws Exception {
        List<Row> rows = asList(new Row("abc", "b"), new Row("123", "2"));

        Header header = Header.Header(Field.of("first", 3), Field.of("second", 1));

        String str = ExportHelper.writeToString(header, rows);
        assertEquals("abcb\r\n1232\r\n", str);

    }

}