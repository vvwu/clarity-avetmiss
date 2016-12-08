package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat0009DisabilityFileRequest;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Field;
import avetmiss.domain.Header;
import avetmiss.domain.Row;

import java.util.List;
import java.util.stream.Collectors;

public class Nat0009DisabilityFile {

    private final static Header header = Header.Header(
            Field.of("Client (Student) Identifier", 10),
            Field.of("Disability Type Identifier", 2));

    public String export(List<Nat0009DisabilityFileRequest> requests) {
        List<Row> rows = exportRaw(requests);
        return ExportHelper.writeToString(header.sizes(), rows);
    }

    List<Row> exportRaw(List<Nat0009DisabilityFileRequest> nat0009DisabilityFileRequests) {
        // If the Disability Flag field displays N or @ in the Client (NAT00080) file,
        // there must be no records for that client in this file.

        List<Row> rows = nat0009DisabilityFileRequests.stream()
                .filter(req -> (req.disabilityTypeIdentifiers != null) && (!req.disabilityTypeIdentifiers.isEmpty()))
                .flatMap(req -> req.disabilityTypeIdentifiers
                        .stream()
                        .map(disabilityIdentifier -> new Row(req.studentID, disabilityIdentifier)))
                .collect(Collectors.toList());

        return rows;


//        List<String[]> rows = newArrayList();
//        for (Nat0009DisabilityFileRequest request : nat0009DisabilityFileRequests) {
//            List<String> identifiers = request.disabilityTypeIdentifiers;
//
//            if(identifiers != null && !identifiers.isEmpty()) {
//                String sid = request.studentID;
//                for (String e : identifiers) {
//                    rows.add(new String[]{sid, e});
//                }
//            }
//        }
//        return rows;
    }
}
