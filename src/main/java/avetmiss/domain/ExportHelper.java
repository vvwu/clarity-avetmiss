package avetmiss.domain;

import java.util.Arrays;
import java.util.List;

public class ExportHelper {

    public static String writeToString(Header header,  List<Row> rows) {
        int[] sizeRow = header.sizes();
        NatFileWriter natFileWriter = new NatFileWriter();
        natFileWriter.append(rows, header.columnNames(), sizeRow);
        return natFileWriter.closeWithAssertion(lengthPerRowAssertion(sizeRow) * rows.size());
    }

    private final static int SIZE_OF_CARRIAGE_RETURN = 2;

    private static int lengthPerRowAssertion(int[] columnSizes) {
        return Arrays.stream(columnSizes).sum() + SIZE_OF_CARRIAGE_RETURN;
    }
}
