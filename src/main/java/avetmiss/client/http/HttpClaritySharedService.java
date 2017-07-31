package avetmiss.client.http;

import avetmiss.client.ClarityShareServiceClient;
import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.OrganizationConstantReadModel;
import avetmiss.client.payload.StudentReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class HttpClaritySharedService implements ClarityShareServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClaritySharedService.class);

    private RestTemplate restTemplate;
    private String serviceBaseUrl;

    protected HttpClaritySharedService() {}
    public HttpClaritySharedService(RestTemplate restTemplate, String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    @Override
    public StudentReadModel findStudent(int studentID) {
        String url = serviceBaseUrl + "/students/{studentID}";

        logger.info("Attempting to find student with studentID: {}, url: '{}'", studentID, url);

        try {
            StudentReadModel response =
                    restTemplate.getForEntity(url, StudentReadModel.class, studentID).getBody();

            return response;
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            logger.error("fail to find student with studentID: '" + studentID + "'", e);
            throw new IllegalStateException("Fail to retrieve student by studentID: " + studentID, e);
        }
    }

    @Override
    public CourseReadModel findCourse(int courseID) {
        String url = serviceBaseUrl + "/courses/{courseID}";

        logger.info("Attempting to find course with courseID: {}, url: '{}'", courseID, url);

        try {
            CourseReadModel response =
                    restTemplate.getForEntity(url, CourseReadModel.class, courseID).getBody();

            return response;
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            logger.error("fail to find course with courseID: '" + courseID + "'", e);
            throw new IllegalStateException("Fail to retrieve course by courseID: " + courseID, e);
        }
    }

    @Override
    public CourseReadModel findCourseByCourseCode(String courseCode) {
        String url = serviceBaseUrl + "/courses?courseCode={courseCode}";

        logger.info("Attempting to find course with courseCode: {}, url: '{}'", courseCode, url);


        try {
            CourseReadModel response =
                    restTemplate.getForEntity(url, CourseReadModel.class, courseCode).getBody();

            return response;
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            logger.error("fail to find course with courseCode: '" + courseCode + "'", e);
            throw new IllegalStateException("Fail to retrieve course by courseCode: " + courseCode, e);
        }
    }

    @Override
    public OrganizationConstantReadModel organizationConstant() {
        String url = serviceBaseUrl + "/organizationConstant";

        logger.info("Attempting to find organizationConstant, url: '{}'", url);

        try {
            return restTemplate.getForEntity(url, OrganizationConstantReadModel.class).getBody();
        } catch (Exception e) {
            logger.error("fail to find 'organizationConstant, url: '" + url + "'", e);
            throw new IllegalStateException("Fail to retrieve " + url, e);
        }
    }
}
