package avetmiss.controller.payload.nat;

import avetmiss.client.payload.OrganizationConstantReadModel;
import avetmiss.domain.EnrolmentSubject;
import avetmiss.export.Client;

import java.util.List;

public class NatFilesRequest {
    public List<Nat00090DisabilityFileRequest> nat0009DisabilityFileRequests;
    public List<Nat00100PriorEducationFileRequest> nat00100PriorEducationFileRequests;
    public List<Nat00130QualificationCompletedFileRequest> nat00130QualificationCompletedFileRequests;
    public OrganizationConstantReadModel nat00010TrainingOrganizationFileRequest;
    public String rtoIdentifier;
    public List<Nat00030CourseFileRequest> nat00030CourseFileRequests;
    public List<EnrolmentSubject> nat00060SubjectFileRequests;
    public List<Client> clientFileRequest;
    public List<EnrolmentFileRequest> enrolmentFileRequests;
}
