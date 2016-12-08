package avetmiss.domain;

public class Row {
    private String[] values;

    public Row(String... values) {
        this.values = values;
    }

    public String[] values() {
        return (values == null) ? new String[0] : values;
    }
}
