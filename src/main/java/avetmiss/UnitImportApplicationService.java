package avetmiss;

import avetmiss.controller.payload.UnitCreateRequest;
import avetmiss.domain.Unit;
import avetmiss.domain.UnitRepository;
import avetmiss.exception.UnitExistException;
import avetmiss.infrastructure.unitFile.NtisUnitTextLineExtractor;
import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.trimToNull;

@Service
public class UnitImportApplicationService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitRepository unitRepository;

    @Transactional
    public void createUnit(UnitCreateRequest unitCreateRequest) {
        logger.debug("Request to create unit: {}", unitCreateRequest);

        String code = trimToNull(unitCreateRequest.code);
        checkArgument(StringUtils.isNotBlank(code), "Unit code is required when creating a new unit");

        Unit existingUnit = unitRepository.findByCode(code);
        if(existingUnit != null) {
            logger.info("Create unit request ignored: '{}', unit code already exist", unitCreateRequest);

            throw new UnitExistException(code);
        }

        Unit unit = new Unit(
                code,
                unitCreateRequest.description,
                unitCreateRequest.fieldOfEducationIdentifier);

        unitRepository.save(unit);

        logger.info("unit added: '{}'", unit);
    }

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
