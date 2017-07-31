package avetmiss.client.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseReadModel {
    public int courseID;
    public String courseIdentifier;
    public String name;
    public Integer nominalHours;

    // H (HigherEducation), Y (Vocational)
    public String vetFlag;

    public String programRecognitionIdentifier;
    public String levelOfEducationIdentifier;
    public String fieldOfEducationIdentifier;
    public String ANZSCOIdentifier;
}
