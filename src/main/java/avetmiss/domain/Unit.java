package avetmiss.domain;

public class Unit {
    private String code;
    private String description;
    private String fieldOfEducationIdentifier;

    public Unit(String code, String description, String fieldOfEducationIdentifier) {
        this.code = code;
        this.description = description;
        this.fieldOfEducationIdentifier = fieldOfEducationIdentifier;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getFieldOfEducationIdentifier() {
        return fieldOfEducationIdentifier;
    }
}
