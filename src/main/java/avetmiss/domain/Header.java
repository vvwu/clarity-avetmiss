package avetmiss.domain;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang.Validate.isTrue;

public class Header {
    private List<Field> fields;

    private Header(List<Field> fields) {
        this.fields = fields;
    }

    public static Header Header(Field ... fields) {
        return new Header(Arrays.asList(fields));
    }

    public static Header Header(int assertTotalRecordLength, Field ... fields) {
        Header header = new Header(Arrays.asList(fields));
        int actualTotalRecordLength = header.totalRecordLength();

        isTrue(assertTotalRecordLength == actualTotalRecordLength,
                "expect: " + assertTotalRecordLength + ", actual: " + actualTotalRecordLength);
        return header;
    }

    public int[] sizes() {
        int[] sizes = new int[fields.size()];

        int i = 0;
        for(Field f: fields) {
            sizes[i++] = f.size();
        }
        return sizes;
    }
    public String[] columnNames() {
        String[] columnNames = new String[fields.size()];

        int i = 0;
        for(Field f: fields) {
            columnNames[i++] = f.name();
        }
        return columnNames;
    }

    private int totalRecordLength() {
        int length = 0;
        for(int size: sizes()) {
            length += size;
        }
        return length;
    }
}
