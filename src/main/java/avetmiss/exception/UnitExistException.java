package avetmiss.exception;

public class UnitExistException extends RuntimeException {
    private String unitCode;

    public UnitExistException(String unitCode) {
        super("unit code already exist: '" + unitCode + "'");
        this.unitCode = unitCode;
    }

    public String unitCode() {
        return unitCode;
    }
}
