package avetmiss.export.natfile;

import avetmiss.export.NatFileConfig;

public class V20140301NATFileConfig implements NatFileConfig {

    private String deliveryProviderABN;

    public V20140301NATFileConfig(String deliveryProviderABN) {
        this.deliveryProviderABN = deliveryProviderABN;
    }

    @Override
    public NAT00030CourseFile nat00030CourseFile() {
        return new NAT00030CourseFile();
    }

    @Override
    public NAT00120EnrolmentFile nat00120EnrolmentFile() {
        return new NAT00120EnrolmentFile(deliveryProviderABN);
    }

    @Override
    public NAT00100PriorEducationFile nat00100PriorEducationFile() {
        return new NAT00100PriorEducationFile();
    }

    @Override
    public NAT00130QualificationCompletedFile nat00130QualificationCompletedFile() {
        return new NAT00130QualificationCompletedFile();
    }

    @Override
    public NAT0009DisabilityFile nat000900DisabilityFile() {
        return new NAT0009DisabilityFile();
    }
}
