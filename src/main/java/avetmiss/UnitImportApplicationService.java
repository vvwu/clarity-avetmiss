package avetmiss;

import avetmiss.domain.Unit;
import avetmiss.domain.UnitRepository;
import avetmiss.infrastructure.unitFile.NtisUnitTextLineExtractor;
import com.google.common.io.Closeables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
public class UnitImportApplicationService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitRepository unitRepository;

    public String importNtisUnits(String ntisUnitsTxt) {
        logger.info("start importing ntisUnits");

        int added = 0;
        int ignored = 0;
        int totalCountBefore = unitRepository.count();

        List<Unit> units = readFromString(ntisUnitsTxt);

        for(Unit unit: units) {
            Unit existingUnit = unitRepository.findByCode(unit.code());

            if(existingUnit != null) {
                ignored ++;
                logger.info("unit ignored: '{}'", unit);
            } else {
                unitRepository.save(unit);
                added ++;

                logger.info("unit added: '{}'", unit);
            }
        }

        int totalCountAfter = unitRepository.count();
        String result = format("added: %s, ignored: %s, total count before import: %s, total count after import: %s",
                added, ignored, totalCountBefore, totalCountAfter);

        logger.info("import completed, {}", result);
        return result;
    }

    private List<Unit> readFromString(String ntisUnitsTxt) {
        NtisUnitTextLineExtractor extractor = new NtisUnitTextLineExtractor();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new StringReader(ntisUnitsTxt));
            List<Unit> list = new ArrayList<Unit>();
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                Unit object = extractor.mapRow(line, lineNumber);
                addToListIfNotNull(list, object);
                lineNumber++;
            }

            Closeables.closeQuietly(reader);
            return list;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private void addToListIfNotNull(List<Unit> list, Unit object) {
        if(object != null) {
            list.add(object);
        }
    }
}
