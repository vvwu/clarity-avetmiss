package avetmiss.export;

import avetmiss.export.natfile.*;

public interface NatFileConfig {

    NAT00030CourseFile nat00030CourseFile();

    NAT00120EnrolmentFile nat00120EnrolmentFile();

    NAT00100PriorEducationFile nat00100PriorEducationFile();

    NAT00130QualificationCompletedFile nat00130QualificationCompletedFile();

    NAT0009DisabilityFile nat000900DisabilityFile();
}
