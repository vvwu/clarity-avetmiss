package avetmiss.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class ExportHelperTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void writeToString() throws Exception {
        int[] sizeRow = {3, 1};
        List<Row> rows = asList(new Row("abc", "b"), new Row("123", "2"));

        String str = ExportHelper.writeToString(sizeRow, rows);
        assertEquals("abcb\r\n1232\r\n", str);

    }

}