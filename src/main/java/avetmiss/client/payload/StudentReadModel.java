package avetmiss.client.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentReadModel {
    public int studentID;

    public boolean international;
    public String vsn;
    public String usi;
    public String sex;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public Date dob;

    public String title;
    public String firstName;
    public String lastName;
    public String suburb;
    public String postCode;

    public String email;
    public String phone;
    public String mobile;

    public EnrolmentInfoReadModel enrolmentInfo;
    public List<StudentCourseReadModel> courses;
}
