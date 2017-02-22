package avetmiss.infrastructure.unitFile;

import avetmiss.domain.Unit;
import avetmiss.util.TextLineMapper;

import static avetmiss.util.StringUtil.isBlank;
import static avetmiss.util.StringUtil.trimString;

public class NtisUnitLineExtractor implements TextLineMapper<Unit> {
    private String tokenSeparator;
    public NtisUnitLineExtractor(String tokenSeparator) {
        this.tokenSeparator = tokenSeparator;
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
        String[] tokens = line.split(tokenSeparator);
        String code = trimString(tokens[0]);
        String name = sanitizeTitle(trimString(tokens[1]));

        String fieldOfEducationIdentifier = null;
        if(tokens.length > 2) {
            fieldOfEducationIdentifier = trimString(tokens[2]);
        }

        if(isBlank(code)) {
            return null;
        }

        return new Unit(code, name, fieldOfEducationIdentifier);
    }

    public static String sanitizeTitle(String title) {
        if(title == null || title.isEmpty()) {
            return null;
        }

        return title.replaceAll("\"", "");
    }
}
