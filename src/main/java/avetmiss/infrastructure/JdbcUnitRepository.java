package avetmiss.infrastructure;

import avetmiss.domain.Unit;
import avetmiss.domain.UnitCodeAlreadyExistException;
import avetmiss.domain.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUnitRepository implements UnitRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Unit findByCode(String unitCode) {
        return internalUnitWithCode(unitCode);
    }

    @Override
    public void save(Unit unit) {
        ensureNoDuplicateUnitCode(unit);

        if (unit.id() == 0) {
            String sql =
                "insert into unit(code, name, field_of_education_identifier) values(?,?,?)";
            jdbcTemplate.update(sql,
                    unit.code(),
                    unit.name(),
                    unit.fieldOfEducationIdentifier());
        } else {
            String sql =
                "update unit set code=?, name=?, field_of_education_identifier=? where (id=?)";
            jdbcTemplate.update(sql,
                    unit.code(),
                    unit.name(),
                    unit.fieldOfEducationIdentifier(),
                    unit.id());
        }
    }

    @Override
    public int count() {
        String sql = "select count(*) from unit";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private void ensureNoDuplicateUnitCode(Unit unit) {
        String code = unit.code();

        Unit existingUnit = internalUnitWithCode(code);
        if ((existingUnit != null) && (unit.id() != existingUnit.id())) {
            throw new UnitCodeAlreadyExistException(code);
        }
    }

    private Unit internalUnitWithCode(String unitCode) {
        String sql =
                "select * from unit where (code=?)";

        try {
            return jdbcTemplate.queryForObject(sql, unitRowMapper(), unitCode);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<Unit> unitRowMapper() {
        return (rs, rowNum) ->
                new Unit(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("field_of_education_identifier"));

    }
}
