package avetmiss.client;

import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.OrganizationConstantReadModel;
import avetmiss.client.payload.StudentReadModel;

public interface ClarityShareServiceClient {
    StudentReadModel findStudent(int studentID);
    CourseReadModel findCourse(int courseID);
    CourseReadModel findCourseByCourseCode(String courseCode);
    OrganizationConstantReadModel organizationConstant();
}
