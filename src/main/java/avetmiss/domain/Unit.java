package avetmiss.domain;

public class Unit {
    private int id;
    private String code;
    private String name;
    private String fieldOfEducationIdentifier;

    // rowMapper
    public Unit(int id, String code, String name, String fieldOfEducationIdentifier) {
        this(code, name, fieldOfEducationIdentifier);
        setId(id);
    }

    public Unit(String code, String name, String fieldOfEducationIdentifier) {
        this.code = code;
        this.name = name;
        this.fieldOfEducationIdentifier = fieldOfEducationIdentifier;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String fieldOfEducationIdentifier() {
        return fieldOfEducationIdentifier;
    }

    @Override
    public String toString() {
        return "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", fieldOfEducationIdentifier='" + fieldOfEducationIdentifier + '\'';
    }
}
