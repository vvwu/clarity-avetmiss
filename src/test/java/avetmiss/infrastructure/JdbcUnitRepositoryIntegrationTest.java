package avetmiss.infrastructure;

import avetmiss.BaseRepositoryIntegrationTest;
import avetmiss.domain.Unit;
import avetmiss.domain.UnitCodeAlreadyExistException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JdbcUnitRepositoryIntegrationTest extends BaseRepositoryIntegrationTest {

    @Autowired
    private JdbcUnitRepository instance;

    @Test
    @Rollback
    public void testSave() {
        Unit unit = givenNewUnit();

        instance.save(unit);

        Unit retrieved = instance.findByCode(unit.code());
        assertThat(retrieved.id() > 0, is(true));
    }

    @Test(expected = UnitCodeAlreadyExistException.class)
    @Rollback
    public void saveShouldFailIfDuplicateCodeExist() {
        Unit unit = givenNewUnit();

        instance.save(unit);

        Unit unitWithSameName = givenNewUnit();
        instance.save(unitWithSameName);
    }

    private Unit givenNewUnit() {
        return new Unit("SWEINS206A", "Comprehending and giving spoken instructions", "120103");
    }
}
