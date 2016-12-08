package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat00100PriorEducationFileRequest;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Field;
import avetmiss.domain.Header;
import avetmiss.domain.Row;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Nat00100PriorEducationFile {

    private final static Header header = Header.Header(14,
            Field.of("Client (Student) Identifier", 10),
            Field.of("Prior Educational Achievement Identifier", 3),
            Field.of("Prior Education Achievement Recognition Identifier", 1));

    public String export(List<Nat00100PriorEducationFileRequest> requests) {
        List<Row> rows = exportRaw(requests);
        return ExportHelper.writeToString(header.sizes(), rows);
    }

    public List<Row> exportRaw(List<Nat00100PriorEducationFileRequest> nat00100PriorEducationFileRequests) {
        List<Row> rows = newArrayList();
        for (Nat00100PriorEducationFileRequest request: nat00100PriorEducationFileRequests) {
            Row row = new Row(
                    request.studentID,
                    request.priorEducationalAchievementIdentifier,
                    request.priorEducationAchievementRecognitionIdentifier);

            rows.add(row);
        }

        return rows;
    }
}
