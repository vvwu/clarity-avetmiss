package avetmiss.export;

import avetmiss.export.natfile.NAT0009DisabilityFile;
import avetmiss.export.natfile.NAT00100PriorEducationFile;
import avetmiss.export.natfile.NAT00120EnrolmentFile;
import avetmiss.export.natfile.NAT00130QualificationCompletedFile;

public interface NatFileConfig {

    NAT00120EnrolmentFile nat00120EnrolmentFile();

    NAT00100PriorEducationFile nat00100PriorEducationFile();

    NAT00130QualificationCompletedFile nat00130QualificationCompletedFile();

    NAT0009DisabilityFile nat000900DisabilityFile();
}
