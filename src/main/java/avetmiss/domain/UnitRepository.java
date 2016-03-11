package avetmiss.domain;

public interface UnitRepository {
    Unit findByCode(String unitCode);

    void save(Unit unit);

    int count();
}
