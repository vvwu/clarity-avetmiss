package clarity.domain;

public interface UnitRepository {
    Unit findByCode(String unitCode);
}
