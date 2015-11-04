package avetmiss.infrastructure.unitFile;

import avetmiss.domain.Unit;
import avetmiss.util.TextLineMapper;

import static avetmiss.util.StringUtil.isBlank;
import static avetmiss.util.StringUtil.trimString;

public class NtisUnitTextLineExtractor implements TextLineMapper<Unit> {

    public NtisUnitTextLineExtractor() {
    }

    @Override
    public Unit mapRow(String line, int lineNum) {
        if(lineNum == 0) {
            return null;
        }

        try {
            return toCompetency(line);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not read line: " + line + ", lineNum: " + lineNum, e);
        }
    }

    protected Unit toCompetency(String line) {
        String[] tokens = line.split("\t");
        String code = trimString(tokens[0]);
        String name = trimString(tokens[1]);

        String fieldOfEducationIdentifier = null;
        if(tokens.length > 2) {
            fieldOfEducationIdentifier = trimString(tokens[2]);
        }

        if(isBlank(code)) {
            return null;
        }

        return new Unit(code, name, fieldOfEducationIdentifier);
    }

}
