package avetmiss.controller.payload;

public class UnitCreateRequest {
    public String code;
    public String description;
    public String fieldOfEducationIdentifier;

    @Override
    public String toString() {
        return "code='" + code + '\'' + ", description='" + description + '\'';
    }
}
