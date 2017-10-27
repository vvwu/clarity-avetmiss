package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.Nat00090DisabilityFileRequest;
import avetmiss.export.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.ArrayUtils.isEmpty;
import static org.apache.commons.lang.ArrayUtils.isNotEmpty;

public class NAT0009DisabilityFile {

    public List<Nat00090DisabilityFileRequest> nat0009DisabilityFileRequests(List<Client> clients) {
        List<Nat00090DisabilityFileRequest> requests = new ArrayList<>();

        clients.stream()
                .filter(Client::hasDisability)
                .forEach(client -> {
                    String[] identifiers = client.disabilityTypeIdentifiers();

                    if (isNotEmpty(identifiers)) {
                        requests.add(new Nat00090DisabilityFileRequest(client.studentId(), asList(identifiers)));
                    }
                });

        return requests;
    }

}
