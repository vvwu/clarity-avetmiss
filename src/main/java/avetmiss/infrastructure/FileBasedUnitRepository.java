package avetmiss.infrastructure;

import avetmiss.domain.Unit;
import avetmiss.domain.UnitRepository;
import avetmiss.infrastructure.unitFile.NtisUnitTextLineExtractor;
import avetmiss.util.ClassPathTextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// @Repository
public class FileBasedUnitRepository implements UnitRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Unit> unitByCode;

    public FileBasedUnitRepository() {

        NtisUnitTextLineExtractor extractor = new NtisUnitTextLineExtractor();

        /** The latest ntis_unit.txt and qualification (course) file can be downloaded from http://www.ncver.edu.au/publications/1452.html **/
        List<Unit> units = new ClassPathTextResource<Unit>("ntis_unit.txt").read(extractor);

        this.unitByCode = units.stream().collect(Collectors.toMap(Unit::code, Function.identity()));

        logger.info("{} units initialized", unitByCode.size());
    }

    @Override
    public Unit findByCode(String unitCode) {
        return this.unitByCode.get(unitCode);
    }

    @Override
    public void save(Unit unit) {
    }

    @Override
    public int count() {
        return unitByCode.size();
    }
}
