package avetmiss.domain;

public enum VetFlag {
    VOCATIONAL("Y"),
    NOT_VOCATIONAL("N");

    VetFlag(String flag) {
        this.flag = flag;
    }
    public final String flag;
}
