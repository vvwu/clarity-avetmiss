package avetmiss.domain;

import java.util.List;

public class ExportHelper {

    public static String writeToString(int[] sizeRow,  List<String[]> rows) {
        NatFileWriter natFileWriter = new NatFileWriter();
        natFileWriter.append(rows, sizeRow);
        return natFileWriter.closeWithAssertion(lengthPerRowAssertion(sizeRow) * rows.size());
    }


    private final static int SIZE_OF_CARRIAGE_RETURN = 2;

    private static int lengthPerRowAssertion(int[] columnSizes) {
        int totalSize = 0;
        for(int size: columnSizes) {
            totalSize += size;
        }
        return totalSize + SIZE_OF_CARRIAGE_RETURN;
    }
}
