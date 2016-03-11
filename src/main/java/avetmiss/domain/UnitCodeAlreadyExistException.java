package avetmiss.domain;

public class UnitCodeAlreadyExistException extends RuntimeException {
    public UnitCodeAlreadyExistException(String unitCode) {
        super("Unit code already exist: '" + unitCode + "'");
    }
}
