package avetmiss.controller.payload.nat;

import java.util.List;

public class NatFilesRequest {
    public List<Nat00090DisabilityFileRequest> nat0009DisabilityFileRequests;
    public List<Nat00100PriorEducationFileRequest> nat00100PriorEducationFileRequests;
    public List<Nat00130QualificationCompletedFileRequest> nat00130QualificationCompletedFileRequests;
    public Nat00010TrainingOrganizationFileRequest nat00010TrainingOrganizationFileRequest;
    public Nat00020TrainingOrganisationDeliveryLocationFileRequest nat00020TrainingOrganisationDeliveryLocationFileRequest;
    public List<Nat00030CourseFileRequest> nat00030CourseFileRequests;
    public List<Nat00060SubjectFileRequest> nat00060SubjectFileRequests;
    public List<ClientFileRequest> clientFileRequest;
    public List<EnrolmentFileRequest> enrolmentFileRequests;
}
