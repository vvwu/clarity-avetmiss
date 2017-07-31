package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.Nat00090DisabilityFileRequest;
import avetmiss.export.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang.ArrayUtils.isEmpty;

public class NAT0009DisabilityFile {

    public List<Nat00090DisabilityFileRequest> nat0009DisabilityFileRequests(List<Client> clients) {
        List<Nat00090DisabilityFileRequest> requests = new ArrayList<>();

        clients.stream()
                .filter(Client::hasDisability)
                .forEach(client -> {
                    String[] identifiers = client.disabilityTypeIdentifiers();
                    if (!isEmpty(identifiers)) {

                        Nat00090DisabilityFileRequest request
                                = new Nat00090DisabilityFileRequest(client.studentId(), Arrays.asList(identifiers));

                        requests.add(request);
                    }
                });
        return requests;
    }

}
