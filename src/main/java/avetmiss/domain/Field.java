package avetmiss.domain;

public class Field {
    private String name;
    private int size;

    public static Field of(String name, int size) {
        return new Field(name, size);
    }

    private Field(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String name() {
        return name;
    }

    public int size() {
        return size;
    }
}
