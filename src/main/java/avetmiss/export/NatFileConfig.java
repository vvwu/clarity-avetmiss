package avetmiss.export;

import avetmiss.export.natfile.*;

public interface NatFileConfig {

    NAT00020TrainingOrganisationDeliveryLocationFile nat00020TrainingOrganisationDeliveryLocationFile();

    NAT00010TrainingOrganizationFile nat00010TrainingOrganizationFile();

    NAT00030CourseFile nat00030CourseFile();

    NAT00060SubjectFile nat00060SubjectFile();

    NAT00120EnrolmentFile nat00120EnrolmentFile();

    NAT00080ClientFile nat00080ClientFile();

    NAT00100PriorEducationFile nat00100PriorEducationFile();

    NAT00130QualificationCompletedFile nat00130QualificationCompletedFile();

    NAT0009DisabilityFile nat000900DisabilityFile();
}
